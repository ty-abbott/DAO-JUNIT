package dao;
import model.User;

import java.sql.*;

/**
 * the data access object for the user table of the database. This is where the interaction with the user table of the
 * database actually happens.
 */
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * this will insert into the user table.
     * @param body - this is the user model that is passed into the method. This has all the information necessary to
     *             insert the user into the database.
     * @return a user model of the user that was inserted, if there was a problem then null will be returned.
     */
    public void insert(User body) throws DataAccessException{
        //this will insert the user in the database
        String sql = "Insert INTO User (username, password, email, firstName, lasName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, body.getUsername());
            stmt.setString(2, body.getPassword());
            stmt.setString(3, body.getEmail());
            stmt.setString(4, body.getFirstname());
            stmt.setString(5, body.getLastname());
            stmt.setString(6, body.getGender());
            stmt.setString(7, body.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error encountered inserting user into user table");
        }
    }

    /**
     * this will return a specific user if it is found in the database.
     * @param username - the information that will be used to try and find the row.
     * @return - the user model will be returned if the user is found. If the user is not found then null will be
     * returned
     */
    public User find(String username) throws DataAccessException {
        //this will return the user if found in the database
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lasName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
            else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DataAccessException("Error encountered when finding a user");
        }
    }

    /**
     * this will delete the user table of the database.
     * @return - this will return true if it worked. Else it returns false.
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM User";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException throwables){
            throwables.printStackTrace();
            throw new DataAccessException("Error encountered when deleting Users");
        }

    }

}



