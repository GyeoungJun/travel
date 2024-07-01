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
public class MyLodgingInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MyLodgingInfoRepository myLodgingInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenLodgingReservationCompleted_then_CREATE_1(
        @Payload LodgingReservationCompleted lodgingReservationCompleted
    ) {
        try {
            if (!lodgingReservationCompleted.validate()) return;

            // view 객체 생성
            MyLodgingInfo myLodgingInfo = new MyLodgingInfo();
            // view 객체에 이벤트의 Value 를 set 함
            myLodgingInfo.setId(lodgingReservationCompleted.getLodgingId());
            myLodgingInfo.setLodgingReservationId(
                lodgingReservationCompleted.getId()
            );
            myLodgingInfo.setUserId(lodgingReservationCompleted.getUserId());
            myLodgingInfo.setStartDate(
                lodgingReservationCompleted.getStartDate()
            );
            myLodgingInfo.setEndDate(lodgingReservationCompleted.getEndDate());
            myLodgingInfo.setPersonCount(
                lodgingReservationCompleted.getPersonCount()
            );
            myLodgingInfo.setStatus(lodgingReservationCompleted.getStatus());
            // view 레파지 토리에 save
            myLodgingInfoRepository.save(myLodgingInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenLodgingReservationRefunded_then_UPDATE_1(
        @Payload LodgingReservationRefunded lodgingReservationRefunded
    ) {
        try {
            if (!lodgingReservationRefunded.validate()) return;
            // view 객체 조회

            List<MyLodgingInfo> myLodgingInfoList = myLodgingInfoRepository.findByStatus(
                lodgingReservationRefunded.getStatus()
            );
            for (MyLodgingInfo myLodgingInfo : myLodgingInfoList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myLodgingInfo.setLodgingReservationId(
                    lodgingReservationRefunded.getId()
                );
                // view 레파지 토리에 save
                myLodgingInfoRepository.save(myLodgingInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
