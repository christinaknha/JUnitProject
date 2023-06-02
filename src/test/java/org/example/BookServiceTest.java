package org.example;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

//  Declare variables of other classes that will be needed to test
    @Mock
    BookService bookDatabaseMock;
    Book book1;
    Book book2;
    Book book3;
    Book book4;

    UserService userDatabaseMock;
    User user;

    User user2;

//  Instantiate and Initialize variables needed for the tests
//  Before does it at the very beginning, prior to running any of the test
    @Before
    public void setup(){

        bookDatabaseMock = new BookService();

        book1 = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 24.99);
        book2 = new Book("Flyte", "Angie Sage", "Fantasy", 12.99);
        book3 = new Book("Sarah's Key", "Tatiana DeRosnay", "Historical Fiction", 24.99);

        bookDatabaseMock.addBook(book1);
        bookDatabaseMock.addBook(book2);
        bookDatabaseMock.addBook(book3);


        userDatabaseMock = new UserService();
        user = new User("HaNhi", "passw0rd", "haNhi@fakemail.com");
        userDatabaseMock.registerUser(user);

    }
//    Tests to see if POM is set up correctly
//    @Test
//    public void testPOMSetUp() {
//        List<String> mockList = mock(List.class);
//        mockList.add("First");
//        when(mockList.get(0)).thenReturn("Mockito");
//        when(mockList.get(1)).thenReturn("JCG");
//        assertEquals("Mockito", mockList.get(0));
//        assertEquals("JCG", mockList.get(1));
//    }

//  Test Search Book Positive Case
    @Test
    public void testSearchBookSuccess(){
//      Searches based on author keyword
        List<Book> authorResults = bookDatabaseMock.searchBook("Tatiana");
        assertEquals(Arrays.asList(book3), authorResults);

//      Searches based on title keyword
        List<Book> titleResult = bookDatabaseMock.searchBook("Harry");
        assertEquals(Arrays.asList(book1), titleResult);

//      Searches based on genre keyword
        List<Book> genreResult = bookDatabaseMock.searchBook("Fantasy");
        assertEquals(Arrays.asList(book1, book2), genreResult);
    }

//  Test Search Book Negative Case
    @Test
    public void testSearchBookNotInSystem(){
//      Test if author keyword is not found
        List<Book> authorResults = bookDatabaseMock.searchBook("James Patterson");
        assertTrue(authorResults.isEmpty());

//      Test if title keyword is not found
        List<Book> titleResults = bookDatabaseMock.searchBook("Blink");
        assertTrue(titleResults.isEmpty());

//      Test if genre keyword is not found
        List<Book> genreResults = bookDatabaseMock.searchBook("spicy");
        assertTrue(genreResults.isEmpty());

    }

//  Test Edge Case if someone enters an empty keyword
    @Test
    public void testSearchBookEmptySearch(){
//      Throws an assertion error so we have to use the try catch method.
        try{
            bookDatabaseMock.searchBook("");
        }catch (AssertionError ae){
            System.out.println("Assertion Error. Please make sure you have entered a valid keyword.");
        }

    }

//  Test Purchase Book Positive Case
    @Test
    public void testPurchaseBookSuccess(){
//      User and book exists
        boolean purchaseSuccess = bookDatabaseMock.purchaseBook(user, book1);
        assertTrue(purchaseSuccess);
    }

//  Test Purchase Book Negative Case
    @Test
    public void testPurchaseBookBookDoesNotExist(){
//      User Exists but book does not.
        boolean purchaseSuccess = bookDatabaseMock.purchaseBook(user, book4);
        assertFalse(purchaseSuccess);

    }

//  Edge Case Purchase Book
    @Test
    public void testPurchaseUserIsNull(){
//      User is null and book does not exist
        boolean purchaseSuccess = bookDatabaseMock.purchaseBook(null, book4);
        assertFalse(purchaseSuccess);
    }



}
