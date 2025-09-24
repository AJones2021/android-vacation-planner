package com.example.d424jones;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.d424jones.database.Repository;


import org.junit.Before;
import org.junit.Test;



public class LoginTest {
    private String rawPassword;
    private String hashedPassword;


    @Before
    public void setUp(){
        rawPassword = "password123";
        hashedPassword = Repository.hashPassword(rawPassword);
    }
    @Test
    public void testCorrectPassword(){
        boolean result = Repository.checkPassword(rawPassword, hashedPassword);
        assertTrue("Password should match when correct password is given", result);
    }

    @Test
    public void testIncorrectPassword(){
        boolean result = Repository.checkPassword("wrongpassword", hashedPassword);
        assertFalse("Password should not match when incorrect password is given", result);
    }
}
