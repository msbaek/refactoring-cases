package pe.msbaek.rfcases.spitphase;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

record Order(String status) {
}

public class CommandLineRunner {
    public static void main(String[] args) {
        try {
            System.out.println(new CommandLineRunner().run(args));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    long run(String[] args) throws IOException {
        CountOrders countOrders = parse(args);
        return countOrders.count();
    }

    private CountOrders parse(String[] args) {
        if(args.length == 0) {
            throw new IllegalArgumentException("enter file name");
        }
        String filename = args[args.length - 1];
        boolean countReadyOnly = Arrays.asList(args).contains("-r");
        return new CountOrders(filename, countReadyOnly);
    }

    private record CountOrders(String filename, boolean countReadyOnly) {
        private long count() throws IOException {
            File input = Paths.get(filename()).toFile();
            ObjectMapper objectMapper = new ObjectMapper();
            Order[] orders = objectMapper.readValue(input, Order[].class);
            if(countReadyOnly()) {
                return Arrays.stream(orders)
                        .filter(order -> "ready".equals(order.status()))
                        .count();
            }
            else {
                return orders.length;
            }
        }
    }
}