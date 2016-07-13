package com.login;

/**
 * Created by Ben_Big on 7/13/16.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontPageController {

    @RequestMapping(value="/",method = RequestMethod.GET)

    public ModelAndView getFistPage(){
        ModelAndView model= new ModelAndView("index");
        return model;
    }

}
