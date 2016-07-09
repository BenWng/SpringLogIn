package com.login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Ben_Big on 7/8/16.
 */


@Entity
public class Userr implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    public Long getId(){return id;}

    private String name;
    private String password;


    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
