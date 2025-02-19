package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.PaymentApplication;
import travel.domain.PaymentCanceled;
import travel.domain.PaymentCompleted;
import travel.domain.PaymentFailed;
import travel.domain.PaymentRefundFailed;
import travel.domain.PaymentRefunded;

@Entity
@Table(name = "Payment_table")
@Data
//<<< DDD / Aggregate Root
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long flightId;

    private Long flightReservationId;

    private Long userId;

    private String status;

    @PostPersist
    public void onPostPersist() {
        PaymentCompleted paymentCompleted = new PaymentCompleted(this);
        paymentCompleted.publishAfterCommit();

        PaymentCanceled paymentCanceled = new PaymentCanceled(this);
        paymentCanceled.publishAfterCommit();

        PaymentRefunded paymentRefunded = new PaymentRefunded(this);
        paymentRefunded.publishAfterCommit();

        PaymentRefundFailed paymentRefundFailed = new PaymentRefundFailed(this);
        paymentRefundFailed.publishAfterCommit();

        PaymentFailed paymentFailed = new PaymentFailed(this);
        paymentFailed.publishAfterCommit();
    }

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    //<<< Clean Arch / Port Method
    public static void receiveCancelAReservationInfo(
        FlightReservationCancelRequested flightReservationCancelRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationCancelRequested.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void receiveAReservationInfo(
        FlightReservationRequested flightReservationRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Payment payment = new Payment();
        repository().save(payment);

        */

        /** Example 2:  finding and process
        
        repository().findById(flightReservationRequested.get???()).ifPresent(payment->{
            
            payment // do something
            repository().save(payment);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
