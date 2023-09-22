package com.koing.server.koing_server.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditingTimeResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    protected LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    protected LocalDateTime updatedAt;

    protected void setBaseTime(AbstractRootEntity abstractRootEntity) {
        this.createdAt = abstractRootEntity.getCreatedAt();
        this.updatedAt = abstractRootEntity.getUpdatedAt();
    }

    protected void setBaseTime(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
