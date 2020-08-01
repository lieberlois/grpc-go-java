package pcbook.sample;

import com.course.grpc.pb.*;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.Random;

public class Generator {
    private Random random;

    public Generator() {
        this.random = new Random();
    }


    public Keyboard newKeyboard() {
        return Keyboard.newBuilder()
                .setLayout(randomKeyboardLayout())
                .setBacklit(random.nextBoolean())
                .build();
    }

    public CPU newCPU() {
        String brand = randomCPUBrand();
        String name = randomCPUName(brand);

        int numCores = randomInt(2, 8);
        int numThreads = randomInt(numCores, 12);

        double minGhz = randomDouble(2.0, 3.5);
        double maxGhz = randomDouble(minGhz, 5.0);

        return CPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setNumCores(numCores)
                .setNumThreads(numThreads)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .build();
    }

    public GPU newGPU() {
        String brand = randomGPUBrand();
        String name = randomGPUName(brand);

        double minGhz = randomDouble(1.0, 1.5);
        double maxGhz = randomDouble(minGhz, 2.0);

        Memory memory = Memory.newBuilder()
                .setValue(randomInt(2, 6))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();

        return GPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .setMemory(memory)
                .build();
    }

    public Memory newRAM() {
        return Memory.newBuilder()
                .setValue(randomInt(4, 64))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
    }

    public Storage newSSD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(128, 1024))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();

        return Storage.newBuilder()
                .setMemory(memory)
                .setDriver(Storage.Driver.SSD)
                .build();
    }

    public Storage newHDD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(1, 6))
                .setUnit(Memory.Unit.TERABYTE)
                .build();

        return Storage.newBuilder()
                .setMemory(memory)
                .setDriver(Storage.Driver.HDD)
                .build();
    }

    public Screen newScreen() {
        int height = randomInt(1080, 4320);
        int width = height * 16 / 9;

        Screen.Resolution resolution = Screen.Resolution.newBuilder()
                .setHeight(height)
                .setWidth(width)
                .build();

        return Screen.newBuilder()
                .setResolution(resolution)
                .setSizeInch(randomFloat(13, 17))
                .setPanel(randomScreenPanel())
                .setMultitouch(random.nextBoolean())
                .build();
    }

    public Laptop newLaptop() {
        String brand = randomLaptopBrand();
        String name = randomLaptopName(brand);

        double weightKg = randomDouble(1.0, 3.0);
        double priceUsd = randomDouble(1500, 3500);
        int releaseYear = randomInt(2015, 2019);

        return Laptop.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setWeightKg(weightKg)
                .setPriceUsd(priceUsd)
                .setReleaseYear(releaseYear)
                .setCpu(newCPU())
                .addGpus(newGPU())
                .addStorages(newSSD())
                .addStorages(newHDD())
                .setScreen(newScreen())
                .setKeyboard(newKeyboard())
                .setRam(newRAM())
                .setUpdatedAt(timestampNow())
                .build();
    }

    private Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    private String randomLaptopBrand() {
        return randomStringFromSet("Apple", "Dell", "Lenovo");
    }

    private String randomLaptopName(String brand) {
        switch (brand) {
            case "Apple":
                return randomStringFromSet("MacBook Pro", "MacBook Air");
            case "Dell":
                return randomStringFromSet("Latitude", "XPS", "Alienware");
            default:
                return randomStringFromSet("Thinkpad X1", "Thinkpad P1");
        }
    }

    private Screen.Panel randomScreenPanel() {
        if (random.nextBoolean())
            return Screen.Panel.IPS;
        return Screen.Panel.OLED;
    }

    private String randomCPUBrand() {
        return randomStringFromSet("Intel", "AMD");
    }

    private String randomCPUName(String brand) {
        if (brand.equals("Intel"))
            return randomStringFromSet("Core i9-9900k", "Core i7-5790k", "Core i5-4790k", "Core i3-3790k");
        else
            return randomStringFromSet("Ryzen 9", "Ryzen 7", "Ryzen 5", "Ryzen 3");
    }

    private String randomGPUBrand() {
        return randomStringFromSet("NVIDIA", "AMD");
    }

    private String randomGPUName(String brand) {
        if (brand.equals("NVIDIA")) {
            return randomStringFromSet("RTX 2060", "RTX 2040", "GTX 1080", "GTX 960");
        }
        return randomStringFromSet("RX 570", "RX 670", "RX 5700-XT", "RX 5800-XT");
    }

    public int randomInt(int min, int max) {
        return min + random.nextInt(max - 1 + 1);
    }

    private double randomDouble(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    private float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    private String randomStringFromSet(String... a) {
        int n = a.length;
        if (n == 0)
            return "";
        return a[random.nextInt(n)];
    }

    private Keyboard.Layout randomKeyboardLayout() {
        switch (random.nextInt(3)) {
            case 1:
                return Keyboard.Layout.QWERTY;
            case 2:
                return Keyboard.Layout.QWERTZ;
            default:
                return Keyboard.Layout.AZERTY;
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        Laptop laptop = generator.newLaptop();
        System.out.println(laptop);

    }
}
