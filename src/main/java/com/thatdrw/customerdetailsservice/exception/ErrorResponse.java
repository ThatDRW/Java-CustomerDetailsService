package com.thatdrw.customerdetailsservice.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private List<String> message = new ArrayList<>();

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
        this.message = Arrays.asList();
    }

    public ErrorResponse(List<String> message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMessage() {
        return this.message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void addMessage(String msg) {
        System.out.println("Add Message Called with message: " + msg);
        this.message.addAll(Arrays.asList(msg));
    }


}
