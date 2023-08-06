package com.junseok.wantedpreonboardingbackend.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpServletUtils {

    public HttpServletRequest getServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();

        return servletRequestAttributes.getRequest();
    }

    public Long getUserIdFromServletRequest() {
        return Long.valueOf((String) this.getServletRequest().getAttribute("userId"));
    }
}
