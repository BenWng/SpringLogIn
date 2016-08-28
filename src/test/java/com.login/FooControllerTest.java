package com.login;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Chloe on 7/12/16.
 * Basic testing for logging in with good credentials
 * Assuming Userr table has a user Tom with password haha
 * and a Userr Jackie with password dragon
 * To package without tests mvn package -Dmaven.test.skip=true
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityJavaConfig.class})
@WebAppConfiguration
public class FooControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    private FooController fc;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @org.junit.Before
    public void setUp() throws Exception {

        System.setOut(new PrintStream(outContent));
        fc = new FooController();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @org.junit.After
    public void tearDown() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
        System.setOut(null);

    }


    @org.junit.Test
    public void widerTesting() throws Exception{

        System.out.println("starting test");

        mvc.perform(post("/login"))
                .andExpect(status().isUnauthorized());

        MvcResult result1 = mvc.perform(post("/login").param("username", "Tom").param("password", "haha")).andExpect(status().isOk()).andReturn();
        MvcResult result2 = mvc.perform(post("/login").param("username", "Jackie").param("password", "dragon")).andExpect(status().isOk()).andReturn();
        mvc.perform(post("/login").param("username", "Tom").param("password", "heehee")).andExpect(status().isUnauthorized());
        mvc.perform(post("/login").param("username", "Jackie").param("password", "fire")).andExpect(status().isUnauthorized());
        mvc.perform(get("http://localhost:8080/foos")).andExpect(status().isUnauthorized());


        //This is the part that isn't working yet..
        HttpSession session = result1.getRequest().getSession();
        //RequestBuilder req  = MockMvcRequestBuilders.get("http://localhost:8080/api/foos").session(session);
        //mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/foos").session((MockHttpSession)session)).andExpect(status().isOk());

        Cookie[] cookie = result1.getResponse().getCookies();
        //System.out.println("Cookie is:" + cookie.toString());
        //Assert.assertNotEquals(cookie.length, 0);


        // Testing that endpoint requires authorization, so calling without it produces an error
        // Also testing that it produces the right error
        // requires deployment
        /*
        RestTemplate template = new RestTemplate();
        try {
            template.getForEntity("http://localhost:8080/foos", String.class);
        } catch (HttpClientErrorException httpExc) {
                assertEquals(httpExc.getStatusCode(), HttpStatus.UNAUTHORIZED);
        }*/

    }

    @org.junit.Test(expected = NullPointerException.class)
    public void noContextThrowsException() throws Exception{
        String ret = fc.justATest();
    }

    /*
    This only tests what justATest() itself does.
     */
    @org.junit.Test
    public void justATest() throws Exception {

        RestTemplate template = new RestTemplate();

        TestingAuthenticationToken authentication = new TestingAuthenticationToken("Tom", "haha");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String ret = fc.justATest();
        Assert.assertEquals(ret, "HelloWorld\n");
        String comp = outContent.toString();
        Integer len = comp.length();
        Assert.assertEquals(comp.substring(len-4, len), "Tom\n");

        // This is the wrong password for Jackie, but this still runs
        // as is expected (accessing the resource requires authentication, not the method itself)
        authentication = new TestingAuthenticationToken("Jackie", "fire");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ret = fc.justATest();
        Assert.assertEquals(ret, "HelloWorld\n");
        comp = outContent.toString();
        len = comp.length();
        Assert.assertEquals(comp.substring(len-7, len), "Jackie\n");

    }

}