package pe.msbaek.rfcases.domain_driven_refactoring;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OfferValueCalculator {
    final RestTemplate restTemplate;

    public OfferValueCalculator(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ResponseEntity<Integer> offerValue(final Member member, final OfferType offerType) {
        return restTemplate.getForEntity(
                String.format("/calculate-offer-value?email=%s&offerType=%s",
                        member.getEmail(),
                        offerType.getName()),
                Integer.class);
    }
}