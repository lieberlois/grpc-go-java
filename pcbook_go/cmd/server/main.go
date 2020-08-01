package main

import (
	"flag"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"net"
	"pcbook/pb"
	"pcbook/service"
)

func main() {
	port := flag.Int("port", 0, "the server port")
	flag.Parse()
	log.Printf("starting server on port %d", *port)


	laptopStore := service.NewInMemoryLaptopStore()
	ratingStore := service.NewInMemoryRatingStore()
	imageStore := service.NewDiskImageStore("img")
	laptopServer := service.NewLaptopServer(laptopStore, imageStore, ratingStore)
	grpcServer := grpc.NewServer()

	pb.RegisterLaptopServiceServer(grpcServer, laptopServer)
	address := fmt.Sprintf("0.0.0.0:%d", *port)

	listener, err := net.Listen("tcp", address)
	if err != nil {
		log.Fatal("cannot start server: ", err)
	}

	err = grpcServer.Serve(listener)
	if err != nil {
		log.Fatal("cannot start server: ", err)
	}
}