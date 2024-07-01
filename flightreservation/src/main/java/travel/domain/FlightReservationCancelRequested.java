package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FlightReservationCancelRequested extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long userId;
    private String status;

    public FlightReservationCancelRequested(FlightReservation aggregate) {
        super(aggregate);
    }

    public FlightReservationCancelRequested() {
        super();
    }
}
//>>> DDD / Domain Event
