package com.login;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ben_Big on 7/7/16.
 */

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{


    @Override
    public void commence(final HttpServletRequest request , final HttpServletResponse response, final AuthenticationException authenticationException)
            throws IOException
    {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");

    }
}
