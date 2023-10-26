package pe.msbaek.rfcases.kt4u;

import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

enum EventProducts {
    PRODUCTS("GOODS_NO", Arrays.asList(
            1L, 2L, 3L, 4L, 5L
    ));

    final String goodsNo;
    final List<Long> values;

    EventProducts(String goodsNo, List<Long> values) {
        this.goodsNo = goodsNo;
        this.values = values;
    }
}

record OrderDetail(Long goodsNo) {
}

interface OrderService {
    List<OrderDetail> setTrackingUrl(List<OrderDetail> orderDtlSellDeliveryAddrObject);
}

interface OrderDao {
    List<OrderDetail> getOrderDtlSellDeliveryAddr();
    List<OrderDetail> getOrderDtlSellGoods();
    OrderDetail getOrderDtlSell();
}

public class OrderController {
    private OrderService orderService;
    private OrderDao orderDao;

    public ModelAndView orderdtlhtml() {
        ModelAndView modelAndView = new ModelAndView();
        // 두라 배송사 추적URL 셋팅
        List<OrderDetail> orderDtlSellDeliveryAddrObject = orderDao.getOrderDtlSellDeliveryAddr();
        orderDtlSellDeliveryAddrObject = orderService.setTrackingUrl(orderDtlSellDeliveryAddrObject);

        modelAndView.addObject("orderDtlSell", orderDao.getOrderDtlSell());
        modelAndView.addObject("orderDtlSellGoods", orderDao.getOrderDtlSellGoods());
        modelAndView.addObject("orderDtlSellDeliveryAddr", orderDtlSellDeliveryAddrObject);
        return modelAndView;
    }

    boolean isEventProduct(List<OrderDetail> orderDtlSellGoods) {
        return orderDtlSellGoods.stream()
                .map(OrderDetail::goodsNo)
                .toList().stream()
                .anyMatch(EventProducts.PRODUCTS.values::contains);
    }
}