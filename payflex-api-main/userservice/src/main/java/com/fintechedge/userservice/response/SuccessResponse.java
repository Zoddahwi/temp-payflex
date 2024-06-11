package com.fintechedge.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class SuccessResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public SuccessResponse(String message) {
        this.success = true;
        this.message = message;
    }

    public SuccessResponse(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

}
