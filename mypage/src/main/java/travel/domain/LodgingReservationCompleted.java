package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import travel.infra.AbstractEvent;

@Data
public class LodgingReservationCompleted extends AbstractEvent {

    private Long id;
    private Long LodgingId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    private Integer personCount;
    private String status;
}
