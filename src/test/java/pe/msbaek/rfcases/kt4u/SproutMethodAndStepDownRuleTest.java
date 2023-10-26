package pe.msbaek.rfcases.kt4u;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SproutMethodAndStepDownRuleTest {
    private OrderDao orderDao;

    @Test
    void isEventProduct() {
        /**
         * Step Down Rule
         *
         * 상품이 이벤트 상품인지 확인하기
         * - 주문한 상품 목록을 읽어오기
         * - 위 결과에서 상품번호만 추출하기(map)
         *   - loop
         *   - map -> long goods_no
         *   - to list
         * - 이 번호들이 이벤트 상품 목록에 포함되어 있는지 확인하기
         */
        List<OrderDetail> orderDtlSellGoods = getOrderDtlSellGoods();
        List<Long> goodsNumbers = orderDtlSellGoods.stream()
                .map(OrderDetail::goodsNo)
                .toList();
        boolean b = goodsNumbers.stream()
                .anyMatch(n -> EventProducts.PRODUCTS.values.contains(n));
        assertThat(b).isTrue();
    }

    private List<OrderDetail> getOrderDtlSellGoods() {
//        return orderDao.getOrderDtlSellGoods();
        return List.of(
                new OrderDetail(1L),
                new OrderDetail(2L)
        );
    }
}