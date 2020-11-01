package com.amberlight.blog.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Base64;

@Component
public class ZuulPreFilter extends ZuulFilter {

//        private Logger log = LoggerFactory.getLogger(this.getClass());

        @Autowired
        private SessionRepository repository;

        @Override
        public String filterType() {
                return FilterConstants.PRE_TYPE;
        }

        @Override
        public int filterOrder() {
                return 0;
        }

        @Override
        public boolean shouldFilter() {

                return true;
        }

        @Override
        public Object run() {
                RequestContext context = RequestContext.getCurrentContext();

                HttpSession httpSession = context.getRequest().getSession();
                httpSession.setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext()
                );
                repository.findById(httpSession.getId());
                context.addZuulRequestHeader("cookie", "SESSION=" + base64Encode(httpSession.getId()));
//                log.info("ZuulPreFilter session proxy: {} and {}", session.getId(), httpSession.getId());

                // Rely on HttpServletRequest to retrieve the correct remote address from upstream X-Forwarded-For header
//                HttpServletRequest request = context.getRequest();
//                String remoteAddr = request.getRemoteAddr();

                // Pass remote address downstream by setting X-Forwarded for header again on Zuul request
//                log.debug("Settings X-Forwarded-For to: {}", remoteAddr);
                context.getZuulRequestHeaders().put("X-Forwarded-For", context.getRequest().getRemoteAddr());



                return null;
        }

        private static String base64Encode(String value) {
                byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
                return new String(encodedCookieBytes);
        }


}
