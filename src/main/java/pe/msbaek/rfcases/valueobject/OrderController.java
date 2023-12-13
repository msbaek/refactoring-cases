package pe.msbaek.rfcases.valueobject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class OrderController {
    public void check(HttpServletRequest request) {
        String DELIVERY_KIND_CD = request.getParameter("delivery").toString();
        Double DELIVERY_WON_PRICE = 0.0;
        Double DELIVERY_PRICE = 0.0;
        String CD_VAL = null;
        Shipping shipping = new Shipping(checkFreeShipping(), DELIVERY_KIND_CD);
        final boolean isNoShippingRequired = shipping.isNoShippingRequired();
        if (shipping.hasCost(isNoShippingRequired)) {
            DELIVERY_KIND_CD = shipping.getDeliveryKindCode();
            DELIVERY_WON_PRICE = shipping.getDeliveryWonPrice();
            DELIVERY_PRICE = shipping.getDeliveryPrice();
            CD_VAL = shipping.getCodeValue();
        }
    }

    private Map checkFreeShipping() {
        throw new UnsupportedOperationException("OrderController::checkFreeShipping not implemented yet");
    }
}