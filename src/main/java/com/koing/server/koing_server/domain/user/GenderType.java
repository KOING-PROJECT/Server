package com.koing.server.koing_server.domain.user;

import com.koing.server.koing_server.common.model.EnumModel;
import lombok.Getter;
import lombok.Setter;

public enum GenderType implements EnumModel {

    Man,
    WOMAN;


    @Override
    public String getGender() {
        return name();
    }

}
