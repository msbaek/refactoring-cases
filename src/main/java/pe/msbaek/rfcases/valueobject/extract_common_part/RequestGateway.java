package pe.msbaek.rfcases.valueobject.extract_common_part;

import java.util.Map;

interface KoreaPostDelivery {
}

public class RequestGateway {
    private void extracted(final KoreaPostDelivery koreaPostDelivery, final Map<String, String> waybillRequestPairs) {
        if (true) {
            if (0 < createVat(koreaPostDelivery).length()) {
                if ("IM".equals(createVat(koreaPostDelivery).substring(0, 2)) || "GB".equals(createVat(koreaPostDelivery).substring(0, 2))) {
                    waybillRequestPairs.put("vatdscrnno", createVat(koreaPostDelivery));
                }
            }
        }
        extracted1(koreaPostDelivery, waybillRequestPairs);
    }

    private void extracted1(KoreaPostDelivery koreaPostDelivery, Map<String, String> waybillRequestPairs) {
        if (0 < createVat(koreaPostDelivery).length()) {
            if ("IM".equals(createVat(koreaPostDelivery).substring(0, 2)) || "GB".equals(createVat(koreaPostDelivery).substring(0, 2))) {
                waybillRequestPairs.put("currunitcd", "USD");
            }
        }
    }

    private String createVat(KoreaPostDelivery koreaPostDelivery) {
        throw new UnsupportedOperationException("RequestGateway::createVat not implemented yet");
    }
}