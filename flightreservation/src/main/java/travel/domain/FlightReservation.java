package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.FlightreservationApplication;
import travel.domain.FlightReservationCancelRequested;
import travel.domain.FlightReservationCanceled;
import travel.domain.FlightReservationCompleted;
import travel.domain.FlightReservationFailed;
import travel.domain.FlightReservationRefunFailed;
import travel.domain.FlightReservationRefunded;
import travel.domain.FlightReservationRequested;

@Entity
@Table(name = "FlightReservation_table")
@Data
//<<< DDD / Aggregate Root
public class FlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long flightId;

    private Long userId;

    private String status;

    @PostPersist
    public void onPostPersist() {
        FlightReservationRequested flightReservationRequested = new FlightReservationRequested(
            this
        );
        flightReservationRequested.publishAfterCommit();

        FlightReservationCancelRequested flightReservationCancelRequested = new FlightReservationCancelRequested(
            this
        );
        flightReservationCancelRequested.publishAfterCommit();

        FlightReservationCompleted flightReservationCompleted = new FlightReservationCompleted(
            this
        );
        flightReservationCompleted.publishAfterCommit();

        FlightReservationCanceled flightReservationCanceled = new FlightReservationCanceled(
            this
        );
        flightReservationCanceled.publishAfterCommit();

        FlightReservationRefunded flightReservationRefunded = new FlightReservationRefunded(
            this
        );
        flightReservationRefunded.publishAfterCommit();

        FlightReservationFailed flightReservationFailed = new FlightReservationFailed(
            this
        );
        flightReservationFailed.publishAfterCommit();

        FlightReservationRefunFailed flightReservationRefunFailed = new FlightReservationRefunFailed(
            this
        );
        flightReservationRefunFailed.publishAfterCommit();
    }

    public static FlightReservationRepository repository() {
        FlightReservationRepository flightReservationRepository = FlightreservationApplication.applicationContext.getBean(
            FlightReservationRepository.class
        );
        return flightReservationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void completeAPayment(PaymentCompleted paymentCompleted) {
        //implement business logic here:

        /** Example 1:  new item 
        FlightReservation flightReservation = new FlightReservation();
        repository().save(flightReservation);

        FlightReservationCompleted flightReservationCompleted = new FlightReservationCompleted(flightReservation);
        flightReservationCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCompleted.get???()).ifPresent(flightReservation->{
            
            flightReservation // do something
            repository().save(flightReservation);

            FlightReservationCompleted flightReservationCompleted = new FlightReservationCompleted(flightReservation);
            flightReservationCompleted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelAPayment(PaymentCanceled paymentCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        FlightReservation flightReservation = new FlightReservation();
        repository().save(flightReservation);

        FlightReservationCanceled flightReservationCanceled = new FlightReservationCanceled(flightReservation);
        flightReservationCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCanceled.get???()).ifPresent(flightReservation->{
            
            flightReservation // do something
            repository().save(flightReservation);

            FlightReservationCanceled flightReservationCanceled = new FlightReservationCanceled(flightReservation);
            flightReservationCanceled.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void refundAPayment(PaymentRefunded paymentRefunded) {
        //implement business logic here:

        /** Example 1:  new item 
        FlightReservation flightReservation = new FlightReservation();
        repository().save(flightReservation);

        FlightReservationRefunded flightReservationRefunded = new FlightReservationRefunded(flightReservation);
        flightReservationRefunded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentRefunded.get???()).ifPresent(flightReservation->{
            
            flightReservation // do something
            repository().save(flightReservation);

            FlightReservationRefunded flightReservationRefunded = new FlightReservationRefunded(flightReservation);
            flightReservationRefunded.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void refundFailAPayment(
        PaymentRefundFailed paymentRefundFailed
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        FlightReservation flightReservation = new FlightReservation();
        repository().save(flightReservation);

        FlightReservationRefunFailed flightReservationRefunFailed = new FlightReservationRefunFailed(flightReservation);
        flightReservationRefunFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentRefundFailed.get???()).ifPresent(flightReservation->{
            
            flightReservation // do something
            repository().save(flightReservation);

            FlightReservationRefunFailed flightReservationRefunFailed = new FlightReservationRefunFailed(flightReservation);
            flightReservationRefunFailed.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void failAPayment(PaymentFailed paymentFailed) {
        //implement business logic here:

        /** Example 1:  new item 
        FlightReservation flightReservation = new FlightReservation();
        repository().save(flightReservation);

        FlightReservationFailed flightReservationFailed = new FlightReservationFailed(flightReservation);
        flightReservationFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentFailed.get???()).ifPresent(flightReservation->{
            
            flightReservation // do something
            repository().save(flightReservation);

            FlightReservationFailed flightReservationFailed = new FlightReservationFailed(flightReservation);
            flightReservationFailed.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
