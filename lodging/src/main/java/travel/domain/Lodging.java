package travel.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import travel.LodgingApplication;

@Entity
@Table(name = "Lodging_table")
@Data
//<<< DDD / Aggregate Root
public class Lodging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer expense;

    private String address;

    private String type;

    private Integer roomCount;

    @PostPersist
    public void onPostPersist() {}

    public static LodgingRepository repository() {
        LodgingRepository lodgingRepository = LodgingApplication.applicationContext.getBean(
            LodgingRepository.class
        );
        return lodgingRepository;
    }

    //<<< Clean Arch / Port Method
    public static void increaseRoomCount(
        LodgingReservationCanceled lodgingReservationCanceled
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Lodging lodging = new Lodging();
        repository().save(lodging);

        */

        /** Example 2:  finding and process
        
        repository().findById(lodgingReservationCanceled.get???()).ifPresent(lodging->{
            
            lodging // do something
            repository().save(lodging);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseRoomCount(
        LodgingReservationRefunded lodgingReservationRefunded
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Lodging lodging = new Lodging();
        repository().save(lodging);

        */

        /** Example 2:  finding and process
        
        repository().findById(lodgingReservationRefunded.get???()).ifPresent(lodging->{
            
            lodging // do something
            repository().save(lodging);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseRoomCount(
        LodgingReservationFailed lodgingReservationFailed
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Lodging lodging = new Lodging();
        repository().save(lodging);

        */

        /** Example 2:  finding and process
        
        repository().findById(lodgingReservationFailed.get???()).ifPresent(lodging->{
            
            lodging // do something
            repository().save(lodging);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void decreaseRoomCount(
        LodgingReservationCompleted lodgingReservationCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Lodging lodging = new Lodging();
        repository().save(lodging);

        */

        /** Example 2:  finding and process
        
        repository().findById(lodgingReservationCompleted.get???()).ifPresent(lodging->{
            
            lodging // do something
            repository().save(lodging);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
