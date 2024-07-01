package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FlightReservationCompleted extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long userId;
    private String status;

    public FlightReservationCompleted(FlightReservation aggregate) {
        super(aggregate);
    }

    public FlightReservationCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
