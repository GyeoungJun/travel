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
    PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationCancelRequested'"
    )
    public void wheneverFlightReservationCancelRequested_ReceiveCancelAReservationInfo(
        @Payload FlightReservationCancelRequested flightReservationCancelRequested
    ) {
        FlightReservationCancelRequested event =
            flightReservationCancelRequested;
        System.out.println(
            "\n\n##### listener ReceiveCancelAReservationInfo : " +
            flightReservationCancelRequested +
            "\n\n"
        );

        // Sample Logic //
        Payment.receiveCancelAReservationInfo(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FlightReservationRequested'"
    )
    public void wheneverFlightReservationRequested_ReceiveAReservationInfo(
        @Payload FlightReservationRequested flightReservationRequested
    ) {
        FlightReservationRequested event = flightReservationRequested;
        System.out.println(
            "\n\n##### listener ReceiveAReservationInfo : " +
            flightReservationRequested +
            "\n\n"
        );

        // Sample Logic //
        Payment.receiveAReservationInfo(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
