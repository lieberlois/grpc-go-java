package service

import (
	"bytes"
	"fmt"
	"github.com/google/uuid"
	"os"
	"sync"
)

// ImageStore is an interface to store laptop images
type ImageStore interface {
	Save(laptopID string, imageType string, imageData bytes.Buffer) (string, error)
}

// DiskImageStore stores images on the disk and its info in memory
type DiskImageStore struct {
	mutex       sync.RWMutex
	imageFolder string
	images      map[string]*ImageInfo
}

// ImageInfo contains information about images
type ImageInfo struct {
	LaptopID string
	Type     string
	Path     string
}

// NewDiskImageStore retuns a new DiskImageStore
func NewDiskImageStore(imageFolder string) *DiskImageStore {
	return &DiskImageStore{
		imageFolder: imageFolder,
		images:      make(map[string]*ImageInfo),
	}
}

func (store *DiskImageStore) Save(laptopID string, imageType string, imageData bytes.Buffer) (string, error) {
	imageID, err := uuid.NewRandom()
	if err != nil {
		return "", fmt.Errorf("cannot generate image id: %w", err)
	}

	imagePath := fmt.Sprintf("%s/%s%s", store.imageFolder, imageID, imageType)
	file, err := os.Create(imagePath)
	if err != nil {
		return "", fmt.Errorf("cannot create image file: %w", err)
	}

	_, err = imageData.WriteTo(file)
	if err != nil {
		return "", fmt.Errorf("cannot write image data: %w", err)
	}

	store.mutex.Lock()
	defer store.mutex.Unlock()

	store.images[imageID.String()] = &ImageInfo{
		LaptopID: laptopID,
		Type:     imageType,
		Path:     imagePath,
	}

	return imageID.String(), nil
}
