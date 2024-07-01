package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.FlightApplication;

@Entity
@Table(name = "Flight_table")
@Data
//<<< DDD / Aggregate Root
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String airLine;

    private Integer seats;

    private String departAirport;

    private String arrivalAirport;

    private Date departTime;

    private Date arrivalTime;

    private Integer expense;

    @PostPersist
    public void onPostPersist() {}

    public static FlightRepository repository() {
        FlightRepository flightRepository = FlightApplication.applicationContext.getBean(
            FlightRepository.class
        );
        return flightRepository;
    }

    //<<< Clean Arch / Port Method
    public static void increaseFlightSeats(
        FlightReservationCanceled flightReservationCanceled
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Flight flight = new Flight();
        repository().save(flight);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationCanceled.get???()).ifPresent(flight->{
            
            flight // do something
            repository().save(flight);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseFlightSeats(
        FlightReservationRefunded flightReservationRefunded
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Flight flight = new Flight();
        repository().save(flight);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationRefunded.get???()).ifPresent(flight->{
            
            flight // do something
            repository().save(flight);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseFlightSeats(
        FlightReservationFailed flightReservationFailed
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Flight flight = new Flight();
        repository().save(flight);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationFailed.get???()).ifPresent(flight->{
            
            flight // do something
            repository().save(flight);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void decreaseFlightSeats(
        FlightReservationCompleted flightReservationCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Flight flight = new Flight();
        repository().save(flight);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationCompleted.get???()).ifPresent(flight->{
            
            flight // do something
            repository().save(flight);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
