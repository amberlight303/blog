package com.amberlight.blog.struct.util;

public class HttpUtil {

    /**
     * Pass-through request id for passing user's request through all internal communications of microservices
     */
    public final static String HEADER_X_PASS_THROUGH_REQUEST_ID = "X-PTRID";

    /**
     * Request Id for communications with users
     */
    public final static String HEADER_X_REQUEST_ID = "X-RID";
    /**
     * Request Id for communications with users
     */
    public final static Integer HTTP_STATUS_BUSINESS_LOGIC_ERROR = 450;



}
