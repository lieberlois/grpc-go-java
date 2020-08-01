package pcbook.serializer;

import com.course.grpc.pb.Laptop;
import org.junit.Assert;
import org.junit.Test;
import pcbook.sample.Generator;

import java.io.IOException;

import static org.junit.Assert.*;

public class SerializerTest {

    @Test
    public void writeAndReadBinaryFile() throws IOException {
        String binaryFile = "laptop.bin";
        Laptop laptop = new Generator().newLaptop();

        Serializer serializer = new Serializer();
        serializer.WriteBinaryFile(laptop, binaryFile);

        Laptop laptop2 = serializer.ReadBinaryFile(binaryFile);
        Assert.assertEquals(laptop, laptop2);
    }
}