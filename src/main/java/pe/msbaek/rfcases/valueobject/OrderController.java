package pe.msbaek.rfcases.valueobject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class OrderController {
    private boolean isNoShippingRequired(Shipping shipping) {
        return "direct".equals(shipping.deliveryCode()) || "donation".equals(shipping.deliveryCode());
    }

    public void check(HttpServletRequest request) {
        final Map deliveryInfo = checkFreeShipping();

        String DELIVERY_KIND_CD = request.getParameter("delivery").toString();
        Double DELIVERY_WON_PRICE = 0.0;
        Double DELIVERY_PRICE = 0.0;
        String CD_VAL = null;
        Shipping shipping = new Shipping(checkFreeShipping(), DELIVERY_KIND_CD);
        final boolean isNoShippingRequired = isNoShippingRequired(shipping);
        if (!isNoShippingRequired && null != shipping.deliveryInfo()) {
            DELIVERY_KIND_CD = shipping.deliveryInfo().get("DELIVERY_KIND_CD").toString();
            DELIVERY_WON_PRICE = Double.parseDouble(shipping.deliveryInfo().get("PRICE").toString());
            DELIVERY_PRICE = Double.parseDouble(shipping.deliveryInfo().get("DISP_PRICE").toString());
            CD_VAL = shipping.deliveryInfo().get("CD_VAL").toString();
        }
    }

    private Map checkFreeShipping() {
        throw new UnsupportedOperationException("OrderController::checkFreeShipping not implemented yet");
    }
}