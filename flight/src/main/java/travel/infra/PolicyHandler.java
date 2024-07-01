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
    FlightRepository flightRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationCanceled'"
    )
    public void wheneverFlightReservationCanceled_IncreaseFlightSeats(
        @Payload FlightReservationCanceled flightReservationCanceled
    ) {
        FlightReservationCanceled event = flightReservationCanceled;
        System.out.println(
            "\n\n##### listener IncreaseFlightSeats : " +
            flightReservationCanceled +
            "\n\n"
        );

        // Sample Logic //
        Flight.increaseFlightSeats(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationRefunded'"
    )
    public void wheneverFlightReservationRefunded_IncreaseFlightSeats(
        @Payload FlightReservationRefunded flightReservationRefunded
    ) {
        FlightReservationRefunded event = flightReservationRefunded;
        System.out.println(
            "\n\n##### listener IncreaseFlightSeats : " +
            flightReservationRefunded +
            "\n\n"
        );

        // Sample Logic //
        Flight.increaseFlightSeats(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationFailed'"
    )
    public void wheneverFlightReservationFailed_IncreaseFlightSeats(
        @Payload FlightReservationFailed flightReservationFailed
    ) {
        FlightReservationFailed event = flightReservationFailed;
        System.out.println(
            "\n\n##### listener IncreaseFlightSeats : " +
            flightReservationFailed +
            "\n\n"
        );

        // Sample Logic //
        Flight.increaseFlightSeats(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationCompleted'"
    )
    public void wheneverFlightReservationCompleted_DecreaseFlightSeats(
        @Payload FlightReservationCompleted flightReservationCompleted
    ) {
        FlightReservationCompleted event = flightReservationCompleted;
        System.out.println(
            "\n\n##### listener DecreaseFlightSeats : " +
            flightReservationCompleted +
            "\n\n"
        );

        // Sample Logic //
        Flight.decreaseFlightSeats(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
