package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class PaymentCompleted extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long flightReservationId;
    private Long userId;
    private String status;
}
