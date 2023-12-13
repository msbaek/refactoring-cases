package pe.msbaek.rfcases.valueobject;

import java.util.Map;

public record Shipping(Map deliveryInfo, String deliveryCode) {
    String getCodeValue() {
        return deliveryInfo().get("CD_VAL").toString();
    }

    double getDeliveryPrice() {
        return Double.parseDouble(deliveryInfo().get("DISP_PRICE").toString());
    }

    double getDeliveryWonPrice() {
        return Double.parseDouble(deliveryInfo().get("PRICE").toString());
    }

    String getDeliveryKindCode() {
        return deliveryInfo().get("DELIVERY_KIND_CD").toString();
    }

    boolean hasCost(boolean isNoShippingRequired) {
        return !isNoShippingRequired && null != deliveryInfo();
    }

    boolean isNoShippingRequired() {
        return "direct".equals(deliveryCode()) || "donation".equals(deliveryCode());
    }
}