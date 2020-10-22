package com.amberlight.cloud.svcpost.config.log4j2;

import lombok.Builder;
import lombok.Value;
import org.apache.logging.log4j.message.Message;

import java.util.Map;

@Value
@Builder
public class CustomMessage implements Message, ICustomMessage {

    private String message;

    private Map<String, Object> customFields;

    @Override
    public String getFormattedMessage() {
//        JSONObject jsonBody = new JSONObject(requestBody);
//        JSONObject jsonToLog = new JSONObject(new HashMap<String, Object>() {{
//            put(TYPE, "custom");
//            put(BODY, jsonBody);
//        }});

//        return jsonToLog.toString();
        return message;
    }

    @Override
    public String getFormat() {
        return message;
    }

    @Override
    public Object[] getParameters() {
        Object[] objects = new Object[2];
        objects[0] = message;
        objects[1] = customFields;
        return objects;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}