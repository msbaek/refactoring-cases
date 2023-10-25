package pe.msbaek.rfcases.kt4u;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

class OrderDetail {
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
}