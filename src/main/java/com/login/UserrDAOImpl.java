package com.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 7/8/16.
 */


@Component
public class UserrDAOImpl implements UserrDAO {

    @PersistenceContext
    private EntityManager entityManager;
    /*
    @Override
    public UserDetails loadUserByUserName(String name)
    throws UsernameNotFoundException
    {
        GrantedAuthorityImpl

    }*/

    @Override
    public void persist(Userr u){
        entityManager.persist(u);
    }

    @Override
    public Userr findByCredential(String name, String password){
        Query query=entityManager.createQuery("select u from Userr u where u.name=:N and u.password=:P");
        query.setParameter("N",name);
        query.setParameter("P",password);
        List<Userr> u=query.getResultList();
        Iterator<Userr> itr=u.iterator();
        if(itr.hasNext()){
            return itr.next();
        }
        else{
            return null;
        }
    }


    @Override
    public List<Userr> findAll(){
        Query query=entityManager.createQuery("select u from Userr u");
        return query.getResultList();
    }
}
