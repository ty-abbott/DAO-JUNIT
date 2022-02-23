package dao;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person person;
    private Person person2;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();

        person = new Person("1234", "tymag", "tyler", "abbott", "m",
                "3", "5", "6");
        person2 = new Person("12342", "tymag", "tyler", "abbott", "m",
                "3", "5", "6");
        Connection conn = db.getConnection();
        pDao = new PersonDAO(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException{
        try {
            pDao.insert(person);
            System.out.println("Starting the insert pass test");
            Person compareTest = pDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person, compareTest);
        } catch (DataAccessException e) {
            fail(e.getMessage());
            System.out.println("Error occured when inserting into data base");
        }
    }
    @Test
    public void insertFail() throws DataAccessException{
        System.out.println("Starting the insert fail test");
        pDao.insert(person);
        assertThrows(DataAccessException.class, () -> pDao.insert(person));
    }
    @Test
    public void findPass(){
        try {
            System.out.println("Starting the find pass test");
            pDao.insert(person);
            Person compareTest = pDao.find(person.getPersonID());
            assertNotNull(compareTest);
            assertEquals(person, compareTest);

        } catch (DataAccessException e){
            fail(e.getMessage());
            System.out.println("Error occured with finding in the database for the first value");
        }
        try {
            System.out.println("finding another user in databse");
            pDao.insert(person2);
            Person compareTest2 = pDao.find(person2.getPersonID());
            assertNotNull(compareTest2);
            assertEquals(person2, compareTest2);
        } catch (DataAccessException e) {
            fail(e.getMessage());
            System.out.println("Error occured with finding in the database for the second value");
        }
    }

    @Test
    public void findFail() {
        try {
            System.out.println("Starting the find fail test");
            Person compareTest = pDao.find(person.getPersonID());
            assertNull(compareTest);
            pDao.insert(person);
            Person compareTest2 = pDao.find(person2.getPersonID());
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
            pDao.insert(person);
            assertNotNull(pDao.find(person.getPersonID()));
            pDao.clear();
            Person compareTest = pDao.find(person.getPersonID());
            assertNull(compareTest);
        } catch (DataAccessException e){
            fail(e.getMessage());
            System.out.println("There was a problem with deleting from the table");
        }
    }
}

