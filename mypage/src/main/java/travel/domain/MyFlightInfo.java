package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MyFlightInfo_table")
@Data
public class MyFlightInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long flightId;
    private Long flightReservationId;
    private Long userId;
    private String departAirport;
    private String arrivalAirport;
    private Date departTime;
    private Date arrivalTime;
    private Integer expense;
    private String status;
}
