package dao;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User user;
    private User user2;
    private UserDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();
        System.out.println("Creating user object for test");
        user = new User("tmag", "password", "t@google.com", "Tyler", "Abbott",
                "m", "789");
        user2 = new User("tmag2", "password2", "t@2google.com", "Tyler2", "Abbott2",
                "m", "789");
        Connection conn = db.getConnection();
        System.out.println("attempting to set connection to database");
        uDAO = new UserDAO(conn);
        System.out.println("attempting to clear database");
        uDAO.clear();

    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException{
        try {
            uDAO.insert(user);
            System.out.println("Starting the insert pass test");
            User compareTest = uDAO.find(user.getUsername());
            assertNotNull(compareTest);
            assertEquals(user, compareTest);
        } catch (DataAccessException e) {
            fail(e.getMessage());
            System.out.println("Error occured when inserting into data base");
        }
    }
    @Test
    public void insertFail() throws DataAccessException{
        System.out.println("Starting the insert fail test");
        uDAO.insert(user);
        assertThrows(DataAccessException.class, () -> uDAO.insert(user));
    }
    @Test
    public void findPass(){
        try {
            System.out.println("Starting the find pass test");
            uDAO.insert(user);
            User compareTest = uDAO.find(user.getUsername());
            assertNotNull(compareTest);
            assertEquals(user, compareTest);

        } catch (DataAccessException e){
            fail(e.getMessage());
            System.out.println("Error occured with finding in the database for the first value");
        }
        try {
            System.out.println("finding another user in databse");
            uDAO.insert(user2);
            User compareTest2 = uDAO.find(user2.getUsername());
            assertNotNull(compareTest2);
            assertEquals(user2, compareTest2);
        } catch (DataAccessException e) {
            fail(e.getMessage());
            System.out.println("Error occured with finding in the database for the second value");
        }
    }

    @Test
    public void findFail() {
        try {
            System.out.println("Starting the find fail test");
            User compareTest = uDAO.find(user.getUsername());
            assertNull(compareTest);
            uDAO.insert(user);
            User compareTest2 = uDAO.find(user2.getUsername());
            assertNull(compareTest2);
        } catch (DataAccessException e) {
            fail(e.getMessage());
            System.out.println("The given user was found when it should not have been");
        }
    }

    @Test
    public void clear() throws DataAccessException {
        try {
            System.out.println("Starting the clear test");
            uDAO.insert(user);
            assertNotNull(uDAO.find(user.getUsername()));
            uDAO.clear();
            User compareTest = uDAO.find(user.getUsername());
            assertNull(compareTest);
        } catch (DataAccessException e){
            fail(e.getMessage());
            System.out.println("There was a problem with deleting from the table");
        }
    }
}
