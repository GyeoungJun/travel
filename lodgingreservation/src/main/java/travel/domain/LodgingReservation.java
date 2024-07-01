package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.LodgingreservationApplication;
import travel.domain.LodgingReservationCancelRequested;
import travel.domain.LodgingReservationCanceled;
import travel.domain.LodgingReservationCompleted;
import travel.domain.LodgingReservationFailed;
import travel.domain.LodgingReservationRefundFailed;
import travel.domain.LodgingReservationRefunded;
import travel.domain.LodgingReservationRequested;

@Entity
@Table(name = "LodgingReservation_table")
@Data
//<<< DDD / Aggregate Root
public class LodgingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lodgingId;

    private Long userId;

    private Date startDate;

    private Date endDate;

    private Integer personCount;

    private String status;

    @PostPersist
    public void onPostPersist() {
        LodgingReservationRequested lodgingReservationRequested = new LodgingReservationRequested(
            this
        );
        lodgingReservationRequested.publishAfterCommit();

        LodgingReservationCancelRequested lodgingReservationCancelRequested = new LodgingReservationCancelRequested(
            this
        );
        lodgingReservationCancelRequested.publishAfterCommit();

        LodgingReservationCompleted lodgingReservationCompleted = new LodgingReservationCompleted(
            this
        );
        lodgingReservationCompleted.publishAfterCommit();

        LodgingReservationCanceled lodgingReservationCanceled = new LodgingReservationCanceled(
            this
        );
        lodgingReservationCanceled.publishAfterCommit();

        LodgingReservationRefunded lodgingReservationRefunded = new LodgingReservationRefunded(
            this
        );
        lodgingReservationRefunded.publishAfterCommit();

        LodgingReservationFailed lodgingReservationFailed = new LodgingReservationFailed(
            this
        );
        lodgingReservationFailed.publishAfterCommit();

        LodgingReservationRefundFailed lodgingReservationRefundFailed = new LodgingReservationRefundFailed(
            this
        );
        lodgingReservationRefundFailed.publishAfterCommit();
    }

    public static LodgingReservationRepository repository() {
        LodgingReservationRepository lodgingReservationRepository = LodgingreservationApplication.applicationContext.getBean(
            LodgingReservationRepository.class
        );
        return lodgingReservationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void completeAPayment(PaymentCompleted paymentCompleted) {
        //implement business logic here:

        /** Example 1:  new item 
        LodgingReservation lodgingReservation = new LodgingReservation();
        repository().save(lodgingReservation);

        LodgingReservationCompleted lodgingReservationCompleted = new LodgingReservationCompleted(lodgingReservation);
        lodgingReservationCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCompleted.get???()).ifPresent(lodgingReservation->{
            
            lodgingReservation // do something
            repository().save(lodgingReservation);

            LodgingReservationCompleted lodgingReservationCompleted = new LodgingReservationCompleted(lodgingReservation);
            lodgingReservationCompleted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void cancelAPayment(PaymentCanceled paymentCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        LodgingReservation lodgingReservation = new LodgingReservation();
        repository().save(lodgingReservation);

        LodgingReservationCanceled lodgingReservationCanceled = new LodgingReservationCanceled(lodgingReservation);
        lodgingReservationCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentCanceled.get???()).ifPresent(lodgingReservation->{
            
            lodgingReservation // do something
            repository().save(lodgingReservation);

            LodgingReservationCanceled lodgingReservationCanceled = new LodgingReservationCanceled(lodgingReservation);
            lodgingReservationCanceled.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void refundAPayment(PaymentRefunded paymentRefunded) {
        //implement business logic here:

        /** Example 1:  new item 
        LodgingReservation lodgingReservation = new LodgingReservation();
        repository().save(lodgingReservation);

        LodgingReservationRefunded lodgingReservationRefunded = new LodgingReservationRefunded(lodgingReservation);
        lodgingReservationRefunded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentRefunded.get???()).ifPresent(lodgingReservation->{
            
            lodgingReservation // do something
            repository().save(lodgingReservation);

            LodgingReservationRefunded lodgingReservationRefunded = new LodgingReservationRefunded(lodgingReservation);
            lodgingReservationRefunded.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void failAPayment(PaymentFailed paymentFailed) {
        //implement business logic here:

        /** Example 1:  new item 
        LodgingReservation lodgingReservation = new LodgingReservation();
        repository().save(lodgingReservation);

        LodgingReservationFailed lodgingReservationFailed = new LodgingReservationFailed(lodgingReservation);
        lodgingReservationFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentFailed.get???()).ifPresent(lodgingReservation->{
            
            lodgingReservation // do something
            repository().save(lodgingReservation);

            LodgingReservationFailed lodgingReservationFailed = new LodgingReservationFailed(lodgingReservation);
            lodgingReservationFailed.publishAfterCommit();

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
        LodgingReservation lodgingReservation = new LodgingReservation();
        repository().save(lodgingReservation);

        LodgingReservationRefundFailed lodgingReservationRefundFailed = new LodgingReservationRefundFailed(lodgingReservation);
        lodgingReservationRefundFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(paymentRefundFailed.get???()).ifPresent(lodgingReservation->{
            
            lodgingReservation // do something
            repository().save(lodgingReservation);

            LodgingReservationRefundFailed lodgingReservationRefundFailed = new LodgingReservationRefundFailed(lodgingReservation);
            lodgingReservationRefundFailed.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
