/**
 * This enum is used to response some messages to api.
 */

package com.zero.loancalculator.model;

public enum MessageModel {
    SUCCESS(200, "OK"),
    DTO_VALIDATION_ERROR(403, "DTO VALIDATION ERROR"),
    NOT_FOUND(404, "NOT FOUND"),
    AUTHENTICATION_FAILED(403, "AUTHENTICATION FAILED"),
    RECORD_AlREADY_EXIST(409, "RECORD ALREADY EXIST"),
    COULD_NOT_UPDATE_RECORD(409, "COULD NOT UPDATE RECORD"),
    COULD_NOT_SAVE_RECORD(409, "COULD NOT SAVE RECORD"),
    COULD_NOT_DELETE_RECORD(409, "COULD NOT DELETE RECORD"),
    UNAUTHORIZED(411, "UNAUTHORIZED"),
    SYSTEM_ERROR(500, "SYSTEM ERROR"),
    USERNAME_TOO_SHORT(503, "USERNAME TOO SHORT"),
    PASSWORD_TOO_SHORT(503, "Password TOO SHORT"),
    INVALID_PHONE_NUMBER(503, "INVALID PHONE NUMBER"),
    WRONG_PASSPORT(406, "WRONG PASSPORT"),
    CREDIT_TAKEN_BEFORE(409, "CREDIT TAKEN BEFORE"),
    ;

    private final String message;
    private final int code;

    MessageModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
