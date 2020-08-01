package service_test

import (
	"context"
	"github.com/stretchr/testify/require"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"pcbook/pb"
	"pcbook/sample"
	"pcbook/service"
	"testing"
)

func TestServerCreateLaptop(t *testing.T) {
	t.Parallel()

	laptopNoID := sample.NewLaptop()
	laptopNoID.Id = ""
	
	laptopInvalidID := sample.NewLaptop()
	laptopInvalidID.Id = "invalid-uuid"

	laptopDuplicateID := sample.NewLaptop()
	storeDuplicateID := service.NewInMemoryLaptopStore()
	err := storeDuplicateID.Save(laptopDuplicateID)
	require.Nil(t, err)

	testCases := []struct{
		name string
		laptop *pb.Laptop
		store service.LaptopStore
		code codes.Code
	}{
		{
			name: "sucess_with_id",
			laptop: sample.NewLaptop(),
			store: service.NewInMemoryLaptopStore(),
			code: codes.OK,
		},
		{
			name: "sucess_without_id",
			laptop: laptopNoID,
			store: service.NewInMemoryLaptopStore(),
			code: codes.OK,
		},
		{
			name: "failed_invalid_id",
			laptop: laptopInvalidID,
			store: service.NewInMemoryLaptopStore(),
			code: codes.InvalidArgument,
		},
		{
			name: "failed_duplicate_id",
			laptop: laptopDuplicateID,
			store: storeDuplicateID,
			code: codes.AlreadyExists,
		},
	}

	for i := range testCases {
		tc := testCases[i]
		t.Run(tc.name, func (t *testing.T) {
			t.Parallel()

			req := &pb.CreateLaptopRequest{
				Laptop: tc.laptop,
			}

			server := service.NewLaptopServer(tc.store, nil, nil)
			res, err := server.CreateLaptop(context.Background(), req)

			if tc.code == codes.OK {
				require.NoError(t, err)
				require.NotNil(t, res)
				require.NotEmpty(t, res.Id)
				if len(tc.laptop.Id) > 0 {
					require.Equal(t, tc.laptop.Id, res.Id)
				}
			} else {
				require.Error(t, err)
				require.Nil(t, res)
				st, ok := status.FromError(err)

				require.True(t, ok)
				require.Equal(t, tc.code, st.Code())
			}	
		})
	}
}

