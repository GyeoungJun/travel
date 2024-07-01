package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class FlightReservationRefunded extends AbstractEvent {

    private Long id;
    private Long flightId;
    private Long userId;
    private String status;
}
