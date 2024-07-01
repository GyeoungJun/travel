package travel.domain;

import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

@Data
@ToString
public class LodgingReservationFailed extends AbstractEvent {

    private Long id;
    private Long lodgingId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    private Integer personCount;
    private String status;
}
