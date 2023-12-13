package pe.msbaek.rfcases.valueobject;

import java.util.Map;

public record Shipping(Map deliveryInfo, String deliveryCode) {
}