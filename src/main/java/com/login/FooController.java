package com.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ben_Big on 7/7/16.
 */


@RestController
@RequestMapping(value="/foos")
public class FooController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public FooController(){
        super();
    }

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public String justATest()
    {


        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String name=auth.getName();
        System.out.println(name);
        return "HelloWorld\n";
    }

}
