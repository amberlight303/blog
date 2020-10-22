package com.amberlight.cloud.svcpost.config.log4j2;


import com.amberlight.cloud.svcpost.config.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Plugin(name = "CustomJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE , printObject = true)
public class CustomJsonLayout extends AbstractStringLayout {

    private static final String DEFAULT_EOL = "\r\n";

    private final ObjectMapper objectMapper = JsonUtils.OBJECT_MAPPER;
    private final boolean locationInfo;

    public CustomJsonLayout(Configuration config, Charset aCharset, Serializer headerSerializer,
                            Serializer footerSerializer, boolean locationInfo) {
        super(config, aCharset, headerSerializer, footerSerializer);
        this.locationInfo = locationInfo;
    }


    @PluginFactory
    public static CustomJsonLayout createLayout(
            @PluginConfiguration final Configuration config,
            @PluginAttribute(value = "charset", defaultString = "UTF-8") final Charset charset,
            @PluginAttribute(value = "locationInfo", defaultBoolean = false) final boolean locationInfo) {
        return new CustomJsonLayout(config, charset, null, null, locationInfo);
    }


    @Override
    public String toSerializable(LogEvent event) {
        Map<String, Object> logMap = new HashMap<>();

        // Log information
        logMap.put("level", event.getLevel().name());
        logMap.put("loggerName", event.getLoggerName());

        String passThroughRequestId = ThreadContext.get("PTRID");
        String userId = ThreadContext.get("UID");
        String localRequestId = ThreadContext.get("LRID");
        String ip = ThreadContext.get("IP");
        String host = ThreadContext.get("HOST");

        if (passThroughRequestId != null) logMap.put("ptrid", passThroughRequestId);
        if (userId != null) logMap.put("uid", userId);
        if (localRequestId != null) logMap.put("lrid", localRequestId);
        if (ip != null) logMap.put("ip", ip);
        if (host != null) logMap.put("host", host);

        // Log location information
        if (locationInfo) {
            final StackTraceElement source = event.getSource();
            Map<String, Object> sourceMap = new HashMap<>();
            sourceMap.put("class", source.getClassName());
            sourceMap.put("method", source.getMethodName());
            sourceMap.put("file", source.getFileName());
            sourceMap.put("line", source.getLineNumber());
            logMap.put("source", sourceMap);
        }

        // Message & custom fields
        if (event.getMessage() instanceof ICustomMessage) {
            Object[] params = event.getMessage().getParameters();
            Map<String, Object> customFields = (Map) params[1];
            logMap.put("message", params[0]);
            if (customFields != null) {
                logMap.putAll(customFields);
            }
        } else {
            logMap.put("message", event.getMessage().getFormattedMessage());
        }

        // Exceptions
        if (event.getThrownProxy() != null) {
            final ThrowableProxy thrownProxy = event.getThrownProxy();
            final Throwable throwable = thrownProxy.getThrowable();

            final String exceptionsClass = throwable.getClass().getCanonicalName();
            if (exceptionsClass != null) {
                logMap.put("exception", exceptionsClass);
            }

            final String exceptionsMessage = throwable.getMessage();
            if (exceptionsMessage != null) {
                logMap.put("cause", exceptionsMessage);
            }

            final String stackTrace = thrownProxy.getExtendedStackTraceAsString("");
            if (stackTrace != null) {
                logMap.put("stacktrace", stackTrace);
            }
        }

        try {
            return objectMapper.writeValueAsString(logMap).concat(DEFAULT_EOL);
        } catch (JsonProcessingException e) {
            return DEFAULT_EOL;
        }

    }
}