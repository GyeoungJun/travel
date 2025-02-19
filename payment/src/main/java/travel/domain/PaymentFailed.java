package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentFailed extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long flightReservationId;
    private Long userId;
    private String status;

    public PaymentFailed(Payment aggregate) {
        super(aggregate);
    }

    public PaymentFailed() {
        super();
    }
}
//>>> DDD / Domain Event
