package pe.msbaek.rfcases.kt4u;

import java.time.Instant;

public record ReadErrorPackingResponse(
        Long id,
        String warehouseName,
        PackingStatus status,
        String packingError,
        String packingErrorMessage,
        Long deliveryId,
        String deliverySeq,
        String trackingNumber,
        String carrierName,
        String boxName,
        Double weight,
        Instant completedAt,
        String updatedUserName,
        String updatedUserLoginId,
        Instant updatedAt
) {
}