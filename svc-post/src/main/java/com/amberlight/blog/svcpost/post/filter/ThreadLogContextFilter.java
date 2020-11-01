package com.amberlight.blog.svcpost.post.filter;

import com.amberlight.blog.struct.security.User;
import com.amberlight.blog.struct.util.HttpUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class ThreadLogContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String passThroughRequestId = request.getHeader(HttpUtil.HEADER_X_PASS_THROUGH_REQUEST_ID);
        if (passThroughRequestId != null && passThroughRequestId.length() == 36) {
            response.addHeader(HttpUtil.HEADER_X_PASS_THROUGH_REQUEST_ID, passThroughRequestId);
            ThreadContext.put("PTRID", passThroughRequestId);
        }
        String localRequestId = UUID.randomUUID().toString();
        ThreadContext.put("UID", user.getId().toString());
        ThreadContext.put("LRID", localRequestId);
        ThreadContext.put("IP", request.getRemoteAddr());
        ThreadContext.put("HOST", request.getServerName() + ":" + request.getServerPort());
        response.addHeader(HttpUtil.HEADER_X_REQUEST_ID, localRequestId);
        filterChain.doFilter(request, response);
        ThreadContext.clearAll();
    }

}
