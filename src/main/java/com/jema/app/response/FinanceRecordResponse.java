package com.jema.app.response;

import java.util.List;

import com.jema.app.entities.FinanceAddRecordRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

public class FinanceRecordResponse {
    private boolean success;
    private String message;
    private Object data;

    public FinanceRecordResponse() {
    }

    public FinanceRecordResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
