package pe.msbaek.rfcases.valueobject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class OrderController {
    private boolean isNoShippingRequired(Shipping shipping) {
        return "direct".equals(shipping.deliveryCode()) || "donation".equals(shipping.deliveryCode());
    }

    public void check(HttpServletRequest request) {
        String DELIVERY_KIND_CD = request.getParameter("delivery").toString();
        Double DELIVERY_WON_PRICE = 0.0;
        Double DELIVERY_PRICE = 0.0;
        String CD_VAL = null;
        Shipping shipping = new Shipping(checkFreeShipping(), DELIVERY_KIND_CD);
        final boolean isNoShippingRequired = isNoShippingRequired(shipping);
        if (hasCost(isNoShippingRequired, shipping)) {
            DELIVERY_KIND_CD = getDeliveryKindCode(shipping);
            DELIVERY_WON_PRICE = getDeliveryWonPrice(shipping);
            DELIVERY_PRICE = getDeliveryPrice(shipping);
            CD_VAL = getCodeValue(shipping);
        }
    }

    private String getCodeValue(Shipping shipping) {
        return shipping.deliveryInfo().get("CD_VAL").toString();
    }

    private double getDeliveryPrice(Shipping shipping) {
        return Double.parseDouble(shipping.deliveryInfo().get("DISP_PRICE").toString());
    }

    private double getDeliveryWonPrice(Shipping shipping) {
        return Double.parseDouble(shipping.deliveryInfo().get("PRICE").toString());
    }

    private String getDeliveryKindCode(Shipping shipping) {
        return shipping.deliveryInfo().get("DELIVERY_KIND_CD").toString();
    }

    private boolean hasCost(boolean isNoShippingRequired, Shipping shipping) {
        return !isNoShippingRequired && null != shipping.deliveryInfo();
    }

    private Map checkFreeShipping() {
        throw new UnsupportedOperationException("OrderController::checkFreeShipping not implemented yet");
    }
}