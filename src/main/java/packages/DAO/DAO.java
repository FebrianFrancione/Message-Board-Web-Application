
package packages.DAO;
import packages.businessLayer.Post;
import packages.businessLayer.User;
import packages.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

//the direct access object that performs the CRUD operations
//a table for posts exists in the database with the columns: (postID, userID, text, attachmentSource, date, tags)
//a table for users exists in the database with the columns: (userID, username, password)
public class DAO {
    //insert user into user database table
    public boolean insert(User user) {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO users (userID,username, password, email) VALUES (?,?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,"5");
            ps.setString(2,user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());

            int i = ps.executeUpdate();

            if(i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println(generatedKeys.getInt(1));
                        System.out.println("Inserted User in User table successful!");
                    }
                    else {
                        throw new SQLException("Inserting user failed");
                    }
                }

                retrieveUserID(user);
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    //insert post into post database table
    public boolean insert(Post post) {
        Connection connection = DBConnection.getConnection();

        try {
            String query = "INSERT INTO posts (userID, text, attachmentSource, date, tags) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, post.getUserID());
            ps.setString(2, post.getText());
            ps.setString(3, post.getAttachment());

            java.sql.Date sqlDate = new java.sql.Date(post.getDate().getTime());

            ps.setDate(4, sqlDate);
            ps.setString(5, post.getTags());

            int i = ps.executeUpdate();

            if(i == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println(generatedKeys.getInt(1));
                        System.out.println("Inserted Post in posts table successful!");
                    }
                    else {
                        throw new SQLException("Inserting user failed");
                    }
                }
                retrievePostID(post);
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    //update user in the user table.
    public boolean update(User user) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET username=?, password=?, email=? WHERE userID=" + user.getUserID());

            ps.setString(1,user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("updated user successfully");
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    //update post in the post table.
    public boolean update(int userID, int postID, String text) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET text=? WHERE postID=" + postID + " AND userID=" + userID);

            ps.setString(1, text);
            //ps.setString(2, post.getAttachment());

            //java.sql.Date sqlDate = new java.sql.Date(post.getDate().getTime());

            //ps.setDate(3, sqlDate);
            //ps.setString(4, post.getTags());

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("updated post successfully");
                return true;
            }else {
                System.out.println("Not authorized to update post");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    //delete a post from the post table.
    public boolean delete(int userID, int postID) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM posts WHERE postID=" + postID + " AND userID=" + userID);

            if(i == 1) {
                System.out.println("deleted post successfully");
                return true;
            }else {
                System.out.println("Not authorized to delete post");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;    }

    //return resultSET. Takes in params in case the search functionality has been used
    //public Post[] retrievePosts(User user, Date from, Date to, String[] tags) {}
    public ArrayList<Post> retrievePosts() {
        Connection connection = DBConnection.getConnection();
        ArrayList<Post> retrievedPosts = new ArrayList<Post>();
        int index = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");

            while(rs.next()) {
                Post post = new Post(rs.getInt("userID"), rs.getString("text"), rs.getString("attachmentSource"), new java.util.Date(rs.getDate("date").getTime()), rs.getString("tags").split(" "));
                post.setPostID(rs.getInt("postID"));
                retrievedPosts.add(post);
                //                retrievedPosts[index].setPostID(rs.getInt("postID"));
//                retrievedPosts[index].setUserID(rs.getInt("userID"));
//                retrievedPosts[index].setText(rs.getString("text"));
//                retrievedPosts[index].setAttachment(rs.getString("attachmentSource"));
//
//                java.util.Date utilDate = new java.util.Date(rs.getDate("date").getTime());
//
//                retrievedPosts[index].setDate(utilDate);
//                retrievedPosts[index].setTags(rs.getString("tags").split(" "));
                //index++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return retrievedPosts;
    }

    //sets userID generated by DB for a specific user
    public void retrieveUserID (User user) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT userID FROM users WHERE username=" + user.getUsername());

            if(rs.next())
            {
                user.setUserID(rs.getInt("userID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public String retrieveUsername (int userID) {
        Connection connection = DBConnection.getConnection();
        String username = "";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM users WHERE userID=" + userID);

            if(rs.next())
            {
                username = rs.getString("username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return username;
    }

//    public void retrieveUserID (User user) {
//        Connection connection = DBConnection.getConnection();
//
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT userID FROM users WHERE username=" + user.getUsername());
//
//            if(rs.next())
//            {
//                user.setUserID(rs.getInt("userID"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                connection.close();
//            } catch(SQLException e){
//                e.printStackTrace();
//            }
//        }
//    }

    //sets postID generated by DB for a specific post
    public void retrievePostID (Post post) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT postID FROM posts WHERE userID=" + post.getUserID());

            if(rs.next())
            {
                post.setPostID(rs.getInt("postID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public User verifyPassword(User user){
        Connection connection = DBConnection.getConnection();
        String password = user.getPassword();
        String username = user.getUsername();
        User returnUser = null;
        try {
            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT userID, email FROM users WHERE username = '" +username + "' AND password = '" + password + "';" );
            ResultSet rs = stmt.executeQuery("SELECT userID FROM users WHERE username = '" +username + "' AND password = '" + password + "';" );

            String ans;
            if(rs.next())
            {
                returnUser = new User(username,password);
                returnUser.setUserID(rs.getInt("userID"));
//                returnUser.setEmail(rs.getNString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                return returnUser;
            } catch(SQLException e){
                e.printStackTrace();
                return returnUser;
            }
        }

    }
}