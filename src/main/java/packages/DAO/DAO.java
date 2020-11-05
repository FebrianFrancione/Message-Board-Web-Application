package packages.DAO;
import packages.businessLayer.Post;
import packages.businessLayer.User;
import packages.database.DBConnection;

import java.sql.*;

//the direct access object that performs the CRUD operations
//a table for posts exists in the database with the columns: (postID, userID, text, attachmentSource, date, tags)
//a table for users exists in the database with the columns: (userID, username, password)
public class DAO {
    //insert user into user database table
    public int insert1(User user) throws ClassNotFoundException {
//        String query = "INSERT INTO users (1, 'username', password, 'email') VALUES (?, ?, ?)";
//        String query = "INSERT INTO users" + " (userID, username, password,email) VALUES " + " (3,'?',?,'?');";
//        String query = "INSERT INTO table_name(id, username, password,email) VALUES (5,?,?,?);";

        Statement statement = null;
        String url = "jdbc:mysql://localhost:3306/mysql_db2";
        String userT = "root";
        String pass = "1234";
//        String query = "create database testest";
//        int result = 0;
        int result = 0;

        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DBConnection.getConnection();
        try (Connection connection = DriverManager.getConnection(url, userT, pass);
//             PreparedStatement ps = connection.prepareStatement(query)

        ) {

            statement = connection.createStatement();
//            ps.setString(2, "febnfeb");
//            ps.setString(3, "china123");
//            ps.setString(4, "winwinwin");
//
////            ps.setInt(1, 5);
////            ps.setString(2, user.getUsername());
////            ps.setString(3, user.getPassword());
////            ps.setString(4, user.getEmail());
//            result = ps.executeUpdate();

            //works
            String part1;
            part1 = user.getUsername();
            String part2;
            part2 = user.getPassword();
            String part3;
            part3 = user.getEmail();
//            String part3 = user.getEmail();
            //works
//            statement.executeUpdate("INSERT INTO table_name" +"(id, username, password, email)" + "values(6,'herro','1232145','duh')");


            result = statement.executeUpdate("INSERT INTO table_name" +"(id, username, password, email)" + "values(11,'"+part1+"','"+part2+"','"+part3+"')");
//            ResultSet resultSet = statement.executeQuery("INSERT INTO table_name (username, password,email) VALUES (hanna,12345,lee@mail); ");
//            ResultSet resultSet = statement.executeQuery("INSERT INTO table_name" +"(username, password, email)" + "values('hanna','12345','lee@mail')" );



//            while(resultSet.next()){
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString(2);
//                System.out.println(id + " " + name);
//            }

//            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

//            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);



//            System.out.println(ps);
//            int i = ps.executeUpdate();
//
//            if(i == 1) {
//                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        System.out.println(generatedKeys.getInt(1));
//                        System.out.println("Inserted User in User table successful!");
//                    } else {
//                        throw new SQLException("Inserting user failed");
//                    }
//                }
//                retrieveUserID(user);
//                return true;
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

/*
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
*/




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
    public boolean update(Post post) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET text=?, attachmentSource=?, date=?, tags=? WHERE postID=" + post.getPostID());

            ps.setString(1, post.getText());
            ps.setString(2, post.getAttachment());

            java.sql.Date sqlDate = new java.sql.Date(post.getDate().getTime());

            ps.setDate(3, sqlDate);
            ps.setString(4, post.getTags());

            int i = ps.executeUpdate();

            if(i == 1) {
                System.out.println("updated post successfully");
                return true;
            }else {
                System.out.println("updated post unsuccessfully");
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
    public boolean delete(Post post) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM posts WHERE postID=" + post.getPostID());

            if(i == 1) {
                System.out.println("deleted post successfully");
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

        return false;    }

    //return resultSET. Takes in params in case the search functionality has been used
    //public Post[] retrievePosts(User user, Date from, Date to, String[] tags) {}
    public Post[] retrievePosts() {
        Connection connection = DBConnection.getConnection();
        Post[] retrievedPosts = new Post[5];
        int index = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");

            while(rs.next()) {
                if (index == 5)
                    break;

                retrievedPosts[index] = new Post(0, null, null, null, null);
                retrievedPosts[index].setPostID(rs.getInt("postID"));
                retrievedPosts[index].setUserID(rs.getInt("userID"));
                retrievedPosts[index].setText(rs.getString("text"));
                retrievedPosts[index].setAttachment(rs.getString("attachmentSource"));

                java.util.Date utilDate = new java.util.Date(rs.getDate("date").getTime());

                retrievedPosts[index].setDate(utilDate);
                retrievedPosts[index].setTags(rs.getString("tags").split(" "));
                index++;
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
}
