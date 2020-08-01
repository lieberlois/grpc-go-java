package service

import "sync"

// RatingStore is an interface for storing laptop ratings
type RatingStore interface {
	Add(laptopID string, score float64) (*Rating, error)
}

type Rating struct {
	Count uint32
	Sum float64
}

// InMemoryRatingStore stores a rating in memory
type InMemoryRatingStore struct {
	mutex sync.RWMutex
	rating map[string]*Rating
}

// NewInMemoryRatingStore creates a new InMemoryRatingStore
func NewInMemoryRatingStore() *InMemoryRatingStore{
	return &InMemoryRatingStore{
		rating: make(map[string]*Rating),
	}
}

func (store *InMemoryRatingStore) Add(laptopID string, score float64) (*Rating, error) {
	store.mutex.Lock()
	defer store.mutex.Unlock()

	rating := store.rating[laptopID]
	if rating == nil {
		rating = &Rating{
			Count: 1,
			Sum:   score,
		}
	} else {
		rating.Count += 1
		rating.Sum += score
	}

	store.rating[laptopID] = rating
	return rating, nil
}

