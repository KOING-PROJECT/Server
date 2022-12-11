package com.koing.server.koing_server.common.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatusCode {

    /**
     * error code
     */
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    DB_HANDLE_FAIL(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    CONFLICT(409),
    EXPIRED_TOKEN(410),
    INVALID_FORMAT_TOKEN(411),
    UNKNOWN_AUTHORITY(412),
    NOT_MATCHED_ACCESS_TOKEN(413),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503);

    private final int status;
}
