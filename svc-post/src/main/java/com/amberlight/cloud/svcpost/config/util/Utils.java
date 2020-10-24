package com.amberlight.cloud.svcpost.config.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.FastDateFormat;


public class Utils {

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("YYYY-MM-DD'T'HH:mm:ss.SSSZ");

}
