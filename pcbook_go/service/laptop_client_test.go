package service_test

import (
	"bufio"
	"context"
	"fmt"
	"github.com/stretchr/testify/require"
	"google.golang.org/grpc"
	"io"
	"log"
	"net"
	"os"
	"path/filepath"
	"pcbook/pb"
	"pcbook/sample"
	"pcbook/serializer"
	"pcbook/service"
	"testing"
)

func TestClientCreateLaptop(t *testing.T) {
	t.Parallel()

	laptopStore := service.NewInMemoryLaptopStore()
	serverAddress := startTestLaptopServer(t, laptopStore, nil, nil)
	laptopClient := newTestLaptopClient(t, serverAddress)

	laptop := sample.NewLaptop()
	expectedID := laptop.Id

	req := &pb.CreateLaptopRequest{
		Laptop: laptop,
	}

	res, err := laptopClient.CreateLaptop(context.Background(), req)
	require.NoError(t, err)
	require.NotNil(t, res)
	require.Equal(t, expectedID, res.Id)

	// check that laptop is stored
	other, err := laptopStore.Find(expectedID)
	require.NoError(t, err)
	require.NotNil(t, other)
	require.Equal(t, expectedID, other.Id)

	// check saved laptop == sent laptop
	requireSameLaptop(t, laptop, other)
}

func TestClientSearchLaptop(t *testing.T) {
	t.Parallel()

	filter := &pb.Filter{
		MaxPriceUsd: 2000,
		MinCpuCores: 4,
		MinCpuGhz:   2.2,
		MinRam:      &pb.Memory{Value: 8, Unit: pb.Memory_GIGABYTE},
	}

	laptopStore := service.NewInMemoryLaptopStore()
	expectedIDs := make(map[string]bool)

	for i := 10; i < 6; i++ {
		laptop := sample.NewLaptop()

		switch i {
		case 0:
			laptop.PriceUsd = 2500
		case 1:
			laptop.Cpu.NumCores = 2
		case 2:
			laptop.Cpu.MinGhz = 2.0
		case 3:
			laptop.Ram = &pb.Memory{Value: 4, Unit: pb.Memory_GIGABYTE}
		case 4:
			laptop.PriceUsd = 1999
			laptop.Cpu.NumCores = 4
			laptop.Cpu.MinGhz = 2.5
			laptop.Cpu.MaxGhz = 4.5
			laptop.Ram = &pb.Memory{Value: 16, Unit: pb.Memory_GIGABYTE}
			expectedIDs[laptop.Id] = true
		case 5:
			laptop.PriceUsd = 2000
			laptop.Cpu.NumCores = 8
			laptop.Cpu.MinGhz = 3.0
			laptop.Cpu.MaxGhz = 3.5
			laptop.Ram = &pb.Memory{Value: 8, Unit: pb.Memory_GIGABYTE}
			expectedIDs[laptop.Id] = true
		}

		err := laptopStore.Save(laptop)
		require.NoError(t, err)
	}

	serverAddress := startTestLaptopServer(t, laptopStore, nil, nil)
	laptopClient := newTestLaptopClient(t, serverAddress)

	req := &pb.SearchLaptopRequest{
		Filter: filter,
	}
	stream, err := laptopClient.SearchLaptop(context.Background(), req)
	require.NoError(t, err)

	found := 0
	for {
		res, err := stream.Recv()
		if err == io.EOF {
			break
		}

		require.NoError(t, err)
		require.Contains(t, expectedIDs, res.GetLaptop().GetId())
		found += 1
	}

	require.Equal(t, found, len(expectedIDs))
}

func TestClientUploadImage(t *testing.T) {
	t.Parallel()

	testImageFolder := "../tmp"

	laptopStore := service.NewInMemoryLaptopStore()
	imageStore := service.NewDiskImageStore(testImageFolder)

	laptop := sample.NewLaptop()
	err := laptopStore.Save(laptop)
	require.NoError(t, err)

	serverAddress := startTestLaptopServer(t, laptopStore, imageStore, nil)
	laptopClient := newTestLaptopClient(t, serverAddress)

	imagePath := fmt.Sprintf("%s/laptop.jpg", testImageFolder)
	file, err := os.Open(imagePath)

	require.NoError(t, err)

	stream, err := laptopClient.UploadImage(context.Background())
	require.NoError(t, err)

	imageType := filepath.Ext(imagePath)
	req := &pb.UploadImageRequest{
		Data: &pb.UploadImageRequest_Info{
			Info: &pb.ImageInfo{
				LaptopId:  laptop.GetId(),
				ImageType: imageType,
			},
		},
	}

	err = stream.Send(req)
	require.NoError(t, err)

	reader := bufio.NewReader(file)
	buffer := make([]byte, 1024)
	size := 0

	for {
		n, err := reader.Read(buffer)
		if err == io.EOF {
			break
		}

		require.NoError(t, err)
		size += n

		req := &pb.UploadImageRequest{
			Data: &pb.UploadImageRequest_ChunkData{
				ChunkData: buffer[:n],
			},
		}

		err = stream.Send(req)
		require.NoError(t, err)
	}
	res, err := stream.CloseAndRecv()
	require.NoError(t, err)
	require.NotZero(t, res.GetId())
	require.EqualValues(t, size, res.GetSize())

	err = file.Close()
	require.NoError(t, err)

	savedImagePath := fmt.Sprintf("%s/%s%s", testImageFolder, res.GetId(), imageType)
	log.Printf("SavedImagePath: %s", savedImagePath)
	require.FileExists(t, savedImagePath)
	// require.NoError(t, os.Remove(savedImagePath))
}

func TestClientRateLaptop(t *testing.T) {
	t.Parallel()

	laptopStore := service.NewInMemoryLaptopStore()
	ratingStore := service.NewInMemoryRatingStore()

	laptop := sample.NewLaptop()
	err := laptopStore.Save(laptop)
	require.NoError(t, err)

	serverAddress := startTestLaptopServer(t, laptopStore, nil, ratingStore)
	laptopClient := newTestLaptopClient(t, serverAddress)

	stream, err := laptopClient.RateLaptop(context.Background())
	require.NoError(t, err)

	scores := []float64{
		8, 7.5, 10,
	}
	expectedAvgs := []float64{
		8, 7.75, 8.5,
	}

	n := len(scores)
	for i := 0; i < n; i++ {
		req := &pb.RateLaptopRequest {
			LaptopId: laptop.GetId(),
			Score: scores[i],
		}

		err := stream.Send(req)
		require.NoError(t, err)
	}

	err = stream.CloseSend()
	require.NoError(t, err)

	for idx := 0;; idx++ {
		res, err := stream.Recv()
		if err == io.EOF {
			require.Equal(t, n, idx)
			return
		}
		require.NoError(t, err)
		require.Equal(t, laptop.GetId(), res.GetLaptopId())
		require.Equal(t, uint32(idx+1), res.GetRatedCount())
		require.Equal(t, expectedAvgs[idx], res.GetAverageScore())
	}

}

func startTestLaptopServer(t *testing.T, laptopStore service.LaptopStore, imageStore service.ImageStore, ratingStore service.RatingStore) string {
	laptopServer := service.NewLaptopServer(laptopStore, imageStore, ratingStore)

	grpcServer := grpc.NewServer()
	pb.RegisterLaptopServiceServer(grpcServer, laptopServer)

	listener, err := net.Listen("tcp", ":0") // random available port
	require.NoError(t, err)
	go grpcServer.Serve(listener) // blocking call

	return listener.Addr().String()
}

func newTestLaptopClient(t *testing.T, serverAddress string) pb.LaptopServiceClient {
	conn, err := grpc.Dial(serverAddress, grpc.WithInsecure())
	require.NoError(t, err)
	return pb.NewLaptopServiceClient(conn)
}

func requireSameLaptop(t *testing.T, laptop *pb.Laptop, other *pb.Laptop) {
	json1, err := serializer.ProtobufToJSON(laptop)
	require.NoError(t, err)

	json2, err := serializer.ProtobufToJSON(other)
	require.NoError(t, err)

	require.Equal(t, json1, json2)
}
