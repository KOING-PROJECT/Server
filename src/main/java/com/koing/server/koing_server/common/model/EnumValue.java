package com.koing.server.koing_server.common.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class EnumValue {

    private String gender;
    private String value;

    public EnumValue(EnumModel enumModel) {
        gender = enumModel.getGender();
    }
}
