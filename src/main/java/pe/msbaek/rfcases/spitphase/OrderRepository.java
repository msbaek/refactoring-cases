package pe.msbaek.rfcases.spitphase;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class OrderRepository {
    public OrderRepository() {
    }

    Order[] readOrders(String filename1) throws IOException {
        File input = Paths.get(filename1).toFile();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, Order[].class);
    }
}