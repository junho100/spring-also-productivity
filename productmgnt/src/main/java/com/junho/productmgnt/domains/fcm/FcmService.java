package com.junho.productmgnt.domains.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessagingEntryPoint;

    public void publishCreateProductTopic(Long userId ){
        Message msg = Message.builder()
            .setTopic(FcmTopic.CREATE_PRODUCT.getTopicName())
            .putData("userId", userId.toString())
            .build();

        try {
            String id = firebaseMessagingEntryPoint.send(msg);
            System.out.println(id);
        }catch (FirebaseMessagingException e){
            throw new BaseException(BaseExceptionStatus.FIREBASE_MESSAGING_EXCEPTION);
        }

    }
}
