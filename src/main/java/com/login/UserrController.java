package com.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ben_Big on 7/8/16.
 */


@RestController
@RequestMapping(value="/user")
@ComponentScan("com.login")
public class UserrController {
    @Autowired
    private UserrDAO userrDAO;

    @RequestMapping(method= RequestMethod.GET)
    public List<Userr> getAll(){
        return userrDAO.findAll();
    }

}
