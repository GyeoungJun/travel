package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FlightReservationRefunFailed extends AbstractEvent {

    private Long id;

    public FlightReservationRefunFailed(FlightReservation aggregate) {
        super(aggregate);
    }

    public FlightReservationRefunFailed() {
        super();
    }
}
//>>> DDD / Domain Event
