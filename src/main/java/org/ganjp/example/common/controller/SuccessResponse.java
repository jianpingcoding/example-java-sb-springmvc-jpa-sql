package org.ganjp.example.common.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


public class SuccessResponse implements Serializable {
    private Timestamp timestamp;
    private String status;
    private String message;
    private Object data;

    public SuccessResponse(String message, Object... data) {
        this.status = "200";
        this.message = message;

        if (data.length == 1) {
            this.data = data[0];
        } else if (data.length >= 2 && data.length % 2 == 0) {
            HashMap map = new HashMap();
            for (int index=0; index < data.length - 1; index=index+2 ) {
              map.put(data[index], data[index+1]);
            }
            this.data = map;
        }

        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
