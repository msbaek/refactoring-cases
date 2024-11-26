package pe.msbaek.rfcases.domain_driven_refactoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface MemberRepository {
    Optional<Member> findById(Long aLong);

    Member save(Member member);
}

interface OfferRepository {
    Offer save(Offer offer);
}
// Required interfaces and classes (These would typically be in separate files)

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

public class AssignOfferHandler {
    private final MemberRepository memberRepository;
    private final OfferRepository offerRepository;
    private final RestTemplate httpClient;
    private final ObjectMapper objectMapper;

    public AssignOfferHandler(
            MemberRepository memberRepository, final OfferRepository offerRepository,
            RestTemplate httpClient,
            ObjectMapper objectMapper) {
        this.memberRepository = memberRepository;
        this.offerRepository = offerRepository;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public void handle(AssignOfferRequest request) throws Exception {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        OfferType offerType = OfferType.from(request.offerTypeId())
                .orElseThrow(() -> new RuntimeException("OfferType not found"));

        // Calculate offer value
        ResponseEntity<Integer> value = httpClient.getForEntity(
                String.format("/calculate-offer-value?email=%s&offerType=%s",
                        member.getEmail(),
                        offerType.getName()),
                Integer.class);

        // Calculate expiration date
        LocalDateTime dateExpiring = switch (offerType.getExpirationType()) {
            case ASSIGNMENT -> LocalDate.now().plusDays(offerType.getDaysValid()).atStartOfDay();
            case FIXED -> {
                if (offerType.getBeginDate() == null) {
                    throw new IllegalStateException("Begin date is required for fixed expiration type");
                }
                yield offerType.getBeginDate().plusDays(offerType.getDaysValid());
            }
        };

        // Assign offer
        Offer offer = Offer.builder()
                .memberAssigned(member)
                .type(offerType)
                .value(value.getBody())
                .dateExpiring(dateExpiring)
                .build();

        member.getAssignedOffers().add(offer);
        member.setNumberOfActiveOffers(member.getNumberOfActiveOffers() + 1);

        offerRepository.save(offer);
        memberRepository.save(member);
    }
}