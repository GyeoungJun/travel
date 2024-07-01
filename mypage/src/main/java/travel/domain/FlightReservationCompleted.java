package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import travel.infra.AbstractEvent;

@Data
public class FlightReservationCompleted extends AbstractEvent {

    private Long id;
    private Long FlightId;
    private Long userId;
    private String status;
}
