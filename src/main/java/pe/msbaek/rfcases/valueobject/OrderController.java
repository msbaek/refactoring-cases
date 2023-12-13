package pe.msbaek.rfcases.valueobject;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class OrderController {
    private boolean isNoShippingRequired(Map deliveryInfo, final String deliveryCode) {
        return "direct".equals(deliveryCode) || "donation".equals(deliveryCode);
    }

    public void check(HttpServletRequest request) {
        final Map deliveryInfo = checkFreeShipping();

        String DELIVERY_KIND_CD = request.getParameter("delivery").toString();
        Double DELIVERY_WON_PRICE = 0.0;
        Double DELIVERY_PRICE = 0.0;
        String CD_VAL = null;
        final boolean isNoShippingRequired = isNoShippingRequired(deliveryInfo, DELIVERY_KIND_CD);
        if (!isNoShippingRequired && null != deliveryInfo) {
            DELIVERY_KIND_CD = deliveryInfo.get("DELIVERY_KIND_CD").toString();
            DELIVERY_WON_PRICE = Double.parseDouble(deliveryInfo.get("PRICE").toString());
            DELIVERY_PRICE = Double.parseDouble(deliveryInfo.get("DISP_PRICE").toString());
            CD_VAL = deliveryInfo.get("CD_VAL").toString();
        }
    }

    private Map checkFreeShipping() {
        throw new UnsupportedOperationException("OrderController::checkFreeShipping not implemented yet");
    }
}