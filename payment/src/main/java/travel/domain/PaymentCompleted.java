package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentCompleted extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long flightReservationId;
    private Long userId;
    private String status;

    public PaymentCompleted(Payment aggregate) {
        super(aggregate);
    }

    public PaymentCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
