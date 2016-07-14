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
    private boolean isAdmin;
    private String email;


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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
