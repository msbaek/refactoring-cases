package pe.msbaek.rfcases.spitphase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineRunnerTest {
    @Test
    @DisplayName("ready 주문의 수를 반환한다.")
    void readyOrderCount() throws Exception {
        // given
        String[] args = {"src/test/resources/orders_ready_2.json"};
        CommandLineRunner runner = new CommandLineRunner();

        // when
        long count = runner.run(args);

        // then
        assertEquals(4, count);
    }

    @Test
    @DisplayName("전체 주문의 수를 반환한다.")
    void allOrderCount() throws Exception {
        // given
        String[] args = {"-r", "src/test/resources/orders_ready_2.json"};
        CommandLineRunner runner = new CommandLineRunner();

        // when
        long count = runner.run(args);

        // then
        assertEquals(2, count);
    }
}