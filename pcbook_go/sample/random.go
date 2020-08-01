package sample

import (
	"github.com/google/uuid"
	"math/rand"
	"pcbook/pb"
	"time"
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

func randomKeyboardLayout() pb.Keyboard_Layout {
	switch rand.Intn(3) {
	case 1:
		return pb.Keyboard_QWERTZ
	case 2:
		return pb.Keyboard_QWERTY
	default:
		return pb.Keyboard_AZERTY
	}
}

func randomCPUBrand() string {
	return randomStringFromSet("Intel", "AMD")
}

func randomGPUBrand() string {
	return randomStringFromSet("NVIDIA", "AMD")
}

func randomLaptopBrand() string {
	return randomStringFromSet("Apple", "Dell", "Lenovo")
}


func randomCPUName(brand string) string {
	if brand == "Intel" {
		return randomStringFromSet("Core i9-9900k", "Core i7-5790k", "Core i5-4790k", "Core i3-3790k")
	} 
	return randomStringFromSet("Ryzen 9", "Ryzen 7", "Ryzen 5", "Ryzen 3")
}

func randomGPUName(brand string) string {
	if brand == "NVIDIA" {
		return randomStringFromSet("RTX 2060", "RTX 2040", "GTX 1080", "GTX 960")
	} 
	return randomStringFromSet("RX 570", "RX 670", "RX 5700-XT", "RX 5800-XT")
}

func randomLaptopName(brand string) string {
	switch brand {
	case "Apple":
			return randomStringFromSet("MacBook Pro", "MacBook Air")
	case "Dell":
			return randomStringFromSet("Latitude", "XPS", "Alienware")
	default:
		return randomStringFromSet("Thinkpad X1", "Thinkpad P1")
	}
}



func randomScreenPanel() pb.Screen_Panel {
	if rand.Intn(2) == 1 {
		return pb.Screen_IPS
	}
	return pb.Screen_OLED
}

func randomScreenResolution() *pb.Screen_Resolution {
	height := randomInt(1080, 4320)
	width := height * 16/9

	res := &pb.Screen_Resolution{
		Height: uint32(height),
		Width: uint32(width),
	}
	return res
}

func randomBool() bool {
	return rand.Intn(2) == 1
}

func randomInt(min int, max int) int {
	return min + rand.Intn(max-min+1)
}

func randomFloat64(min float64, max float64) float64 {
	return min + rand.Float64() * (max-min)
}

func randomFloat32(min float32, max float32) float32 {
	return min + rand.Float32() * (max-min)
}

func randomStringFromSet(a ...string) string {
	n := len(a)
	if n == 0{
		return ""
	}
	return a[rand.Intn(n)]
}

func randomID() string {
	return uuid.New().String()
}