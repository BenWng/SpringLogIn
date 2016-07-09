package com.login;

import java.util.List;

/**
 * Created by Ben_Big on 7/8/16.
 */
public interface UserrDAO {
    public void persist(Userr u);

    public Userr findByCredential(String name, String password);

    public List<Userr> findAll();


}
