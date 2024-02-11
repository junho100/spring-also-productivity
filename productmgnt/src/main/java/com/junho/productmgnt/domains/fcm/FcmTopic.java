package com.junho.productmgnt.domains.fcm;

import lombok.Getter;

@Getter
public enum FcmTopic {
    CREATE_PRODUCT("user.create-product.admin");
    private String topicName;

    FcmTopic(String topicName) {
        this.topicName = topicName;
    }
}
