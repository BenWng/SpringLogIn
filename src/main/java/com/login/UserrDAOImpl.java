package com.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by Ben_Big on 7/8/16.
 */

/*
reference: http://stackoverflow.com/questions/14268451/spring-security-userdetailsservice-implementation-login-fails
 */


@Component
public class UserrDAOImpl implements UserrDAO, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException
    {
        Userr u=null;
        Query query=entityManager.createQuery("select u from Userr u where u.eamil=:E");
        query.setParameter("E",email);
        List<Userr> userrList=query.getResultList();
        Iterator<Userr> itr=userrList.iterator();
        if(itr.hasNext()){
            u=itr.next();
        }
        else{
            throw new UsernameNotFoundException("No such user:"+email);
        }

        // boolean fields required for User (the one in security.core.userdetails.User)
        boolean accountNonExpired=true;
        boolean credentialNonExpired=true;
        boolean accountNonLocked=true;
        boolean accountIsEnabled=true;


        List<GrantedAuthority> authList=new ArrayList<>();
        authList.add(new SimpleGrantedAuthority("USER"));
        if (u.isAdmin()){
            authList.add(new SimpleGrantedAuthority("ADMIN"));
        }


        return new User(
                u.getEmail(),
                u.getPassword(),
                accountIsEnabled,
                accountNonExpired,
                credentialNonExpired,
                accountNonLocked,
                authList
                );
    }



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
