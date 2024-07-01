package travel.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import travel.domain.*;
import travel.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class LodgingReservationRefundFailed extends AbstractEvent {

    private Long id;
    private Long lodgingId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    private Integer personCount;
    private String status;

    public LodgingReservationRefundFailed(LodgingReservation aggregate) {
        super(aggregate);
    }

    public LodgingReservationRefundFailed() {
        super();
    }
}
//>>> DDD / Domain Event
