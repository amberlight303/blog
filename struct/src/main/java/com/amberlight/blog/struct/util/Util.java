package com.amberlight.blog.struct.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.FastDateFormat;


public class Util {

    public static ObjectMapper MAPPER = new ObjectMapper();

    public static FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("YYYY-MM-DD'T'HH:mm:ss.SSSZ");

}
