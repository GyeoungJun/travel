package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FlightReservationRefunded extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long userId;
    private String status;

    public FlightReservationRefunded(FlightReservation aggregate) {
        super(aggregate);
    }

    public FlightReservationRefunded() {
        super();
    }
}
//>>> DDD / Domain Event
