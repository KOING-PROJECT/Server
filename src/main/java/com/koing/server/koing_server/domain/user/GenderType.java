package com.koing.server.koing_server.domain.user;

import com.koing.server.koing_server.common.model.EnumModel;

public enum GenderType implements EnumModel {

    MAN,
    WOMAN,
    UNKNOWN;

    @Override
    public String getGender() {
        return name();
    }

}
