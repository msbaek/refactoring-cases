package pe.msbaek.rfcases.spitphase;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

record Order(String status) {
}

public class CommandLineRunner {
    public static void main(String[] args) {
        try {
            if(args.length == 0) {
                throw new IllegalArgumentException("enter file name");
            }
            String filename = args[args.length - 1];
            File input = Paths.get(filename).toFile();
            ObjectMapper objectMapper = new ObjectMapper();
            Order[] orders = objectMapper.readValue(input, Order[].class);
            if(Arrays.stream(args).anyMatch(arg -> "-r".equals(arg))) {
                System.out.println(Arrays.stream(orders)
                        .filter(order -> "ready".equals(order.status()))
                        .count());
            }
            else {
                System.out.println(orders.length);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}