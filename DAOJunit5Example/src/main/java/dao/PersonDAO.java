package dao;

import model.Person;

import java.sql.*;

/**
 * this class interacts with the person table of the database.
 */
public class PersonDAO {
    private final Connection conn;

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * inserts a person into the database.
     * @param body - has all the information necessary to input the person into the database
     * @return - if there was no problem then the person model is returned. If there was then null is returned.
     */
    public void insert(Person body) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID," +
                "spoudeID) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, body.getPersonID());
            stmt.setString(2, body.getAssociatedUsername());
            stmt.setString(3, body.getFirstName());
            stmt.setString(4, body.getLastName());
            stmt.setString(5, body.getGender());
            stmt.setString(6, body.getFatherID());
            stmt.setString(7, body.getMotherID());
            stmt.setString(8, body.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * this reads the family of the person that is passed in
     * @param body - this is the person model, it will be used to find the associated family members.
     * @return - an array of persons, if there was a problem then it returns null.
     */
    public Person[] readFamily(Person body){return null;}

    /**
     * this looks for and reads a specific family member.
     * @param personID - the information that will be used to find the specific person.
     * @return - returns the model of the person found. returns null if not found.
     */
    public Person find(String personID) throws DataAccessException{
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spoudeID"));
                return person;
            }
            else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * deletes the person table of the database.
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM PERSON";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when deleting from the database");
        }
    }
}
