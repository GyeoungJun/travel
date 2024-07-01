package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MyLodgingInfo_table")
@Data
public class MyLodgingInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long lodgingId;
    private Long lodgingReservationId;
    private Long userId;
    private Integer expense;
    private String type;
    private String address;
    private Date startDate;
    private Date endDate;
    private Integer personCount;
    private String status;
}
