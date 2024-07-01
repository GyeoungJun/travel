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
    LodgingReservationRepository lodgingReservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentCompleted'"
    )
    public void wheneverPaymentCompleted_CompleteAPayment(
        @Payload PaymentCompleted paymentCompleted
    ) {
        PaymentCompleted event = paymentCompleted;
        System.out.println(
            "\n\n##### listener CompleteAPayment : " + paymentCompleted + "\n\n"
        );

        // Sample Logic //
        LodgingReservation.completeAPayment(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentCanceled'"
    )
    public void wheneverPaymentCanceled_CancelAPayment(
        @Payload PaymentCanceled paymentCanceled
    ) {
        PaymentCanceled event = paymentCanceled;
        System.out.println(
            "\n\n##### listener CancelAPayment : " + paymentCanceled + "\n\n"
        );

        // Sample Logic //
        LodgingReservation.cancelAPayment(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentRefunded'"
    )
    public void wheneverPaymentRefunded_RefundAPayment(
        @Payload PaymentRefunded paymentRefunded
    ) {
        PaymentRefunded event = paymentRefunded;
        System.out.println(
            "\n\n##### listener RefundAPayment : " + paymentRefunded + "\n\n"
        );

        // Sample Logic //
        LodgingReservation.refundAPayment(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentFailed'"
    )
    public void wheneverPaymentFailed_FailAPayment(
        @Payload PaymentFailed paymentFailed
    ) {
        PaymentFailed event = paymentFailed;
        System.out.println(
            "\n\n##### listener FailAPayment : " + paymentFailed + "\n\n"
        );

        // Sample Logic //
        LodgingReservation.failAPayment(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PaymentRefundFailed'"
    )
    public void wheneverPaymentRefundFailed_RefundFailAPayment(
        @Payload PaymentRefundFailed paymentRefundFailed
    ) {
        PaymentRefundFailed event = paymentRefundFailed;
        System.out.println(
            "\n\n##### listener RefundFailAPayment : " +
            paymentRefundFailed +
            "\n\n"
        );

        // Sample Logic //
        LodgingReservation.refundFailAPayment(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
