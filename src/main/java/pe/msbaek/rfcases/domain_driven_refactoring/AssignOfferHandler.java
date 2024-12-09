package pe.msbaek.rfcases.domain_driven_refactoring;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
interface MemberRepository {
    Optional<Member> findById(Long aLong);

    Member save(Member member);
}

@Repository
interface OfferRepository {
    Offer save(Offer offer);
}
// Required interfaces and classes (These would typically be in separate files)
@Configuration
class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Builder
record Offer(Member memberAssigned, OfferType type, Integer value, LocalDateTime dateExpiring) {
}

enum ExpirationType {ASSIGNMENT, FIXED}

record AssignOfferRequest(Long memberId, Long offerTypeId) {}

@Data
class Member {
    private Long id;
    private String email;
    private List<Offer> assignedOffers = new ArrayList<>();
    private int numberOfActiveOffers;
}

@Data
class OfferType {
    private Long id;
    private String name;
    private ExpirationType expirationType;
    private int daysValid;
    private LocalDateTime beginDate;

    public static Optional<OfferType> from(final Long typeId) {
        throw new UnsupportedOperationException("OfferType::from not implemented yet");
    }
}

@RestController
@Service
@Transactional
public class AssignOfferHandler {
    private final MemberRepository memberRepository;
    private final OfferRepository offerRepository;
    private final OfferValueCalculator offerValueCalculator;

    public AssignOfferHandler(
            MemberRepository memberRepository, final OfferRepository offerRepository,
            final OfferValueCalculator offerValueCalculator) {
        this.memberRepository = memberRepository;
        this.offerRepository = offerRepository;
        this.offerValueCalculator = offerValueCalculator;
    }

    @PostMapping("/assign-offer")
    public void handle(@RequestBody AssignOfferRequest request) throws Exception {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        OfferType offerType = OfferType.from(request.offerTypeId())
                .orElseThrow(() -> new RuntimeException("OfferType not found"));

        // Calculate offer value
        ResponseEntity<Integer> value = offerValueCalculator.offerValue(member, offerType);

        // Calculate expiration date
        LocalDateTime dateExpiring = expirationDate(offerType);

        // Assign offer
        Offer offer = assignOffer(member, offerType, value, dateExpiring);

        offerRepository.save(offer);
        memberRepository.save(member);
    }

    private Offer assignOffer(final Member member, final OfferType offerType, final ResponseEntity<Integer> value, final LocalDateTime dateExpiring) {
        Offer offer = Offer.builder()
                .memberAssigned(member)
                .type(offerType)
                .value(value.getBody())
                .dateExpiring(dateExpiring)
                .build();

        member.getAssignedOffers().add(offer);
        member.setNumberOfActiveOffers(member.getNumberOfActiveOffers() + 1);
        return offer;
    }

    private LocalDateTime expirationDate(final OfferType offerType) {
        return switch (offerType.getExpirationType()) {
            case ASSIGNMENT -> LocalDate.now().plusDays(offerType.getDaysValid()).atStartOfDay();
            case FIXED -> {
                if (offerType.getBeginDate() == null) {
                    throw new IllegalStateException("Begin date is required for fixed expiration type");
                }
                yield offerType.getBeginDate().plusDays(offerType.getDaysValid());
            }
        };
    }
}