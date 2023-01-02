package online.ronikier.todo.interfaces.interceptors;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.library.Cryptonite;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
//@Component
//@SessionScope
public class CookieInterceptor extends HandlerInterceptorAdapter {

    private boolean cookiePresent = false;

    private final String COOKIE_NAME = "TODO_TOKEN";

    @Deprecated
    @Setter
    private String cookieValue = Cryptonite.encode("dupa");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (cookiePresent(request)) {
            log.info("KUKKIEE! " + cookieValue);
        } else {
            response.addCookie(new Cookie(COOKIE_NAME, cookieValue));
        }
        cookiePresent = true;
        return true;
    }

    @Deprecated
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        processCookie(cookieValue);
        if (!cookiePresent) response.addCookie(new Cookie(COOKIE_NAME, cookieValue));
    }

    private void processCookie(String cookieValue) {
        log.debug("Cookie post processing ...");
        cookieValue = cookieValue;
        cookiePresent = false;
    }

    private boolean cookiePresent(HttpServletRequest request) {
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(COOKIE_NAME)).filter(myCookie -> myCookie.getValue().equals(myCookie)).findAny().isPresent();
    }

}
