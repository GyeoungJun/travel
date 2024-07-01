package travel.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import travel.config.kafka.KafkaProcessor;
import travel.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    LodgingRepository lodgingRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LodgingReservationCanceled'"
    )
    public void wheneverLodgingReservationCanceled_IncreaseRoomCount(
        @Payload LodgingReservationCanceled lodgingReservationCanceled
    ) {
        LodgingReservationCanceled event = lodgingReservationCanceled;
        System.out.println(
            "\n\n##### listener IncreaseRoomCount : " +
            lodgingReservationCanceled +
            "\n\n"
        );

        // Sample Logic //
        Lodging.increaseRoomCount(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LodgingReservationRefunded'"
    )
    public void wheneverLodgingReservationRefunded_IncreaseRoomCount(
        @Payload LodgingReservationRefunded lodgingReservationRefunded
    ) {
        LodgingReservationRefunded event = lodgingReservationRefunded;
        System.out.println(
            "\n\n##### listener IncreaseRoomCount : " +
            lodgingReservationRefunded +
            "\n\n"
        );

        // Sample Logic //
        Lodging.increaseRoomCount(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LodgingReservationFailed'"
    )
    public void wheneverLodgingReservationFailed_IncreaseRoomCount(
        @Payload LodgingReservationFailed lodgingReservationFailed
    ) {
        LodgingReservationFailed event = lodgingReservationFailed;
        System.out.println(
            "\n\n##### listener IncreaseRoomCount : " +
            lodgingReservationFailed +
            "\n\n"
        );

        // Sample Logic //
        Lodging.increaseRoomCount(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='LodgingReservationCompleted'"
    )
    public void wheneverLodgingReservationCompleted_DecreaseRoomCount(
        @Payload LodgingReservationCompleted lodgingReservationCompleted
    ) {
        LodgingReservationCompleted event = lodgingReservationCompleted;
        System.out.println(
            "\n\n##### listener DecreaseRoomCount : " +
            lodgingReservationCompleted +
            "\n\n"
        );

        // Sample Logic //
        Lodging.decreaseRoomCount(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
