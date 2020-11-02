package com.amberlight.blog.svcpost.config.log4j2;


import org.apache.logging.log4j.message.Message;

import java.util.Map;

public class CustomMessage implements Message, ICustomMessage {

    private Integer logEventId;

    private String message;

    private Map<String, Object> customFields;

    public CustomMessage(Integer logEventId, String message) {
        this.logEventId = logEventId;
        this.message = message;
    }

    public CustomMessage(Integer logEventId, String message, Map<String, Object> customFields) {
        this.logEventId = logEventId;
        this.message = message;
        this.customFields = customFields;
    }

    public CustomMessage(String message) {
        this.message = message;
    }

    public CustomMessage(String message, Map<String, Object> customFields) {
        this.message = message;
        this.customFields = customFields;
    }

    @Override
    public String getFormattedMessage() {
        return message;
    }

    @Override
    public String getFormat() {
        return message;
    }

    @Override
    public Object[] getParameters() {
        Object[] objects = new Object[3];
        objects[0] = message;
        objects[1] = logEventId;
        objects[2] = customFields;
        return objects;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    public Integer getLogEventId() {
        return logEventId;
    }

    public void setLogEventId(Integer logEventId) {
        this.logEventId = logEventId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, Object> customFields) {
        this.customFields = customFields;
    }
}