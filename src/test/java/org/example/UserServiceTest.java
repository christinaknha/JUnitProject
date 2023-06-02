package org.example;

import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //  Declare variables of other classes that will be needed to test
    @Mock

    UserService userDatabaseMock;
    User user;
    User user2;

    //  Instantiate and Initialize variables needed for the tests
//  Before does it at the very beginning, prior to running any of the test
    @Before
    public void setup(){

        userDatabaseMock = new UserService();
        user = new User("HaNhi", "password", "haNhi@fakemail.com");
        user2 = new User("tonio", "1234", "tonio@fakemail.com");

        userDatabaseMock.registerUser(user);
        userDatabaseMock.registerUser(user2);

    }

//  Test User Registration Positive Case
    @Test
    public void testUserRegistrationSuccess(){
//      Creates a new User
        User user3 = new User("marbs", "fishAreFood", "marbs@fakemail.com");
//      Confirms new user was added to the database
        boolean registrationSuccess = userDatabaseMock.registerUser(user3);
        assertTrue(registrationSuccess);

    }

//  Test User Registration Negative Case
    @Test
    public void userRegistrationFailUserExists(){
//      User being added already exists in the database
        boolean registrationSuccess = userDatabaseMock.registerUser(user);
        assertFalse(registrationSuccess);
    }

//  Edge Case User Registration
    @Test
    public void userRegistrationFailUserIsNull(){
//      User information is null. Throws an Assertion Error so try catch method is needed.
        try {
            User user4 = new User(null, null, null);
            boolean registrationSuccess = userDatabaseMock.registerUser(user4);
        }catch(AssertionError ae){
            System.out.println("Assertion Error. Please make sure you have entered valid user data.");
        }

    }

//  Test User Login Positive Case
    @Test
    public void testUserLoginSuccess(){
//      Username and Password match
        User loginSuccess = userDatabaseMock.loginUser("HaNhi","password");
        assertEquals(user, loginSuccess);
    }

//  Test User Login Negative Case
    @Test
    public void testUserLoginFailWrongPassword(){
//      Username and password exist but do not match
        User loginSuccess = userDatabaseMock.loginUser("tonio","password");
        assertNotEquals(user2, loginSuccess);
    }

//  Edge Case User Login
    @Test
    public void testUserLoginFailUsernameAndPasswordNull(){
//      Username does not exist
        User loginSuccess = userDatabaseMock.loginUser(null,"password");
        assertNotEquals(user, loginSuccess);

//      Password is null
        User loginSuccessPassNull = userDatabaseMock.loginUser("marbs",null);
        assertNotEquals(user, loginSuccessPassNull);
    }
}
