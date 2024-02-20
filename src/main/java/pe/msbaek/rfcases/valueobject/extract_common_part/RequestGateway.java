package pe.msbaek.rfcases.valueobject.extract_common_part;

import java.util.Map;

interface KoreaPostDelivery {
}

public class RequestGateway {
    private void extracted(final KoreaPostDelivery koreaPostDelivery, final Map<String, String> waybillRequestPairs) {
        if(false) {
            final String vat = createVat(koreaPostDelivery);
            if (0 < vat.length()) {
                if ("IM".equals(vat.substring(0, 2)) || "GB".equals(vat.substring(0, 2))) {
                    waybillRequestPairs.put("currunitcd", "USD");
                }
            }
        } else {
            final String vat = createVat(koreaPostDelivery);
            if (0 < vat.length()) {
                if ("IM".equals(vat.substring(0, 2)) || "GB".equals(vat.substring(0, 2))) {
                    waybillRequestPairs.put("vatdscrnno", vat);
                }
            }
            if (0 < vat.length()) {
                if ("IM".equals(vat.substring(0, 2)) || "GB".equals(vat.substring(0, 2))) {
                    waybillRequestPairs.put("currunitcd", "USD");
                }
            }
        }
    }

    private String createVat(KoreaPostDelivery koreaPostDelivery) {
        throw new UnsupportedOperationException("RequestGateway::createVat not implemented yet");
    }
}