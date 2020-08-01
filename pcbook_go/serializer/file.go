package serializer

import (
	"fmt"
	"github.com/golang/protobuf/proto"
	"io/ioutil"
)

// WriteProtobufToBinaryFile writes protocol buffer message to binary file
func WriteProtobufToBinaryFile(message proto.Message, filename string) error {
	data, err := proto.Marshal(message)
	if err != nil {
		return fmt.Errorf("Cannot marshal proto message to binary: %w", err)
	}

	err = ioutil.WriteFile(filename, data, 0644)
	if err != nil {
		return fmt.Errorf("Cannot write binary data to file: %w", err)
	}

	return nil
}

// ReadProtobufFromBinaryFile reads protocol buffer message from binary file
func ReadProtobufFromBinaryFile(filename string, message proto.Message) error {
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return fmt.Errorf("Error loading binary file: %w", err)
	}

	err = proto.Unmarshal(data, message)
	if err != nil {
		return fmt.Errorf("Cannot unmarshal data from binary file: %w", err)
	}

	return nil
}

// WriteProtobufToJSONFile writes protocol buffer message to JSON file
func WriteProtobufToJSONFile(message proto.Message, filename string) error {
	data, err := ProtobufToJSON(message)
	if err != nil {
		return fmt.Errorf("Cannot marshal proto message to JSON: %w", err)
	}

	err = ioutil.WriteFile(filename, []byte(data), 0644)
	if err != nil {
		return fmt.Errorf("Cannot write JSON data to file: %w", err)
	}

	return nil
}
