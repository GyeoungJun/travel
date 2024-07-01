package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import travel.infra.AbstractEvent;

@Data
public class FlightReservationRefunded extends AbstractEvent {

    private Long id;
    private Long FlightId;
    private Long userId;
    private String status;
}
