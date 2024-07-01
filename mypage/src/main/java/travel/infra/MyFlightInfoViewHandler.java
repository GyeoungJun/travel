package travel.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import travel.config.kafka.KafkaProcessor;
import travel.domain.*;

@Service
public class MyFlightInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MyFlightInfoRepository myFlightInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFlightReservationCompleted_then_CREATE_1(
        @Payload FlightReservationCompleted flightReservationCompleted
    ) {
        try {
            if (!flightReservationCompleted.validate()) return;

            // view 객체 생성
            MyFlightInfo myFlightInfo = new MyFlightInfo();
            // view 객체에 이벤트의 Value 를 set 함
            myFlightInfo.setId(flightReservationCompleted.getFlightId());
            myFlightInfo.setFlightReservationId(
                flightReservationCompleted.getId()
            );
            myFlightInfo.setUserId(flightReservationCompleted.getUserId());
            myFlightInfo.setStatus(flightReservationCompleted.getStatus());
            // view 레파지 토리에 save
            myFlightInfoRepository.save(myFlightInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFlightReservationRefunded_then_UPDATE_1(
        @Payload FlightReservationRefunded flightReservationRefunded
    ) {
        try {
            if (!flightReservationRefunded.validate()) return;
            // view 객체 조회

            List<MyFlightInfo> myFlightInfoList = myFlightInfoRepository.findByStatus(
                flightReservationRefunded.getStatus()
            );
            for (MyFlightInfo myFlightInfo : myFlightInfoList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myFlightInfo.setFlightReservationId(
                    flightReservationRefunded.getId()
                );
                // view 레파지 토리에 save
                myFlightInfoRepository.save(myFlightInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
