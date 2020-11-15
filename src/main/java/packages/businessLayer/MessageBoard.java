package packages.businessLayer;

import packages.DAO.DAO;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import packages.database.DBConnection;


public class MessageBoard {
    //Our Data Access Object, this is responsible inserting, updating, deleting, retrieving from the database
    private DAO daoObj = new DAO();


    //    public void checkUserID(String username,String password){
//        daoObj.retrieveUserID(username,password);
//    }
    //Sign up functionality not to be implemented
    public void signUp(String username, String password) {
        User newUser = new User(username, password);

        /*insert into database*/
        daoObj.insert(newUser);
    }

    //modify account functionality not to be implemented
    public void modifyAccount(User user, String username, String password, String email) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        /*update user in database*/
        daoObj.update(user);
    }

    public void createPost(int userID, String text, InputStream att, String tags, String fileName, long size, String fileType) throws IOException {
        Date date = new Timestamp(System.currentTimeMillis());
        Post post = new Post(userID, text, att, date, tags, fileName, size, fileType);

        daoObj.insert(post);
    }

    public void deletePost(int userID, int postID) {
        daoObj.deletePost(userID, postID);
    }

    public void updatePost(int userID, int postID, String text, InputStream inputStream, String tags) {
        daoObj.update(userID, postID, text, inputStream, tags);
    }

    public ArrayList<Post> searchCases(String option, String username, String fromDate, String toDate, String tag) {

        if (option instanceof String) {
            switch (option) {
                case "searchUser":
                    return daoObj.searchPosts(username);
                case "searchByDate":
                    return daoObj.searchByDates(fromDate, toDate);
                case "searchByTags":
                    return daoObj.searchByTags(tag);
                case "searchAll":
                    return daoObj.searchAll(username, fromDate, toDate, tag);
            }
        }
        return null;
    }

    // display searched items
    public String search(String option, String username, String fromDate, String toDate, String tag, int i, String reverse) throws IOException {
        System.out.println(option + username + fromDate + toDate + tag + i + reverse);
        String out = "";

        if (option == null && username == null) {
            out = display(i, reverse);
            return out;
        }

        ArrayList<Post> searchedAllPosts = searchCases(option, username, fromDate, toDate, tag);
        ArrayList<Post> filesSearchedAllPosts = daoObj.retrieveFiles(searchedAllPosts);

        String lastUpdated = "";

        if (searchedAllPosts != null) {
            if (reverse.equals("true")) {
                Collections.reverse(filesSearchedAllPosts);
            }
        }

        if (searchedAllPosts != null) {
            int counter = 0;
            for (Post post : filesSearchedAllPosts) {
                if (counter == i) {
                    break;
                }
                lastUpdated = "";
                if (post.getLastUpdated() != null) {
                    lastUpdated += "Last Updated: " + post.getLastUpdated();
                }

                out += "" +
                        "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                        "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">" +
                        "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                        "<div>Post id: " + post.getPostID() + "</div>" +
                        "</div>" +
                        "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                        "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                        "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

                BufferedImage image = null;

                if (post.getAttachment() != null) {
                    byte[] imageBytes = new byte[(int) post.getAttachment().available()];
                    post.getAttachment().read(imageBytes, 0, imageBytes.length);
                    String imageString = Base64.encodeBase64String(imageBytes);
                    out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
                } else {
                    out += "</div>";
                }
                counter++;
            }
        }
        return out;
    }

    public void displayRecent() {
        /*retreive posts in-order from database*/
    }

    public String display(int i, String reverse) throws IOException {
        String out = "";
        ArrayList<Post> postsWithoutFiles = daoObj.retrievePosts();
        ArrayList<Post> postsList = daoObj.retrieveFiles(postsWithoutFiles);
        String lastUpdated = "";

        if (reverse.equals("true")) {
            Collections.reverse(postsList);
        }

        int counter = 0;
        for (Post post : postsList) {
            if (counter == i) {
                break;
            }
            lastUpdated = "";
            if (post.getLastUpdated() != null) {
                lastUpdated += "Last Updated: " + post.getLastUpdated();
            }

            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                    "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">" +
                    "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                    "<div>Post id: " + post.getPostID() + "</div>" +
                    "</div>" +
                    "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                    "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                    "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

            BufferedImage image = null;

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int) post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
            } else {
                out += "</div>";
            }
            counter++;
        }
        return out;
    }

    public ArrayList<Integer> retrievePostIDs() {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for (Post post : daoObj.retrievePosts()) {
            IDs.add(post.getPostID());
        }
        return IDs;
    }

    public int verifyUser(String username, String password) {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //hashing the user given password
        byte[] encodedhash = digest.digest(password.getBytes());

        //converting byte array to String of hex
        String hashedpass = DatatypeConverter.printHexBinary(encodedhash);
        User temp = new User(username, hashedpass);

        User returned = daoObj.verifyPassword(temp);

        if (returned != null) {
            return returned.getUserID();
        }
        return -1;
    }

    public void download(String fileID, ServletContext context, HttpServletResponse response, int BufferSize) {
        Connection conn = null;
        Statement stmt = null;
        String fileName = null;
        Blob blob;

        try {

            //get conn to DB
            conn = DBConnection.getConnection();

            System.out.println("db connected");

            stmt = (Statement) conn.createStatement();

            //request the file Blob and the FileName from the DB
//            ResultSet rs1 = stmt.executeQuery("select file,fileName from files where fileID = " + fileID);
            ResultSet rs1 = stmt.executeQuery("select file,fileName from files where fileID = " + fileID);

            if (rs1.next()) {
                // get the file name
                fileName = rs1.getString("fileName");
                //get the blob
                blob = rs1.getBlob("file");

                InputStream inputStream = blob.getBinaryStream();

                //ServletContext context = getServletContext();

                // find the mimeType of the file . Set binary if nothing is found
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {
                    mimeType = "application/binary";
                }

                // setting the Content type header, the content length header, and the Content disposition header
                response.setContentType(mimeType);
                response.setContentLength(inputStream.available());     //inputStream.available() will give the file length
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader("Content-Disposition", headerValue);

                // get the servlet output stream
                OutputStream outStream = response.getOutputStream();

                //buffer size is defined at the top
                byte[] buffer = new byte[BufferSize];
                int bytesRead = -1;

                //writing the the outputStream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.flush();
                outStream.close();

                /*
                byte[] imgData = rs1.getBytes("file");//Here....... r1.getBytes() extract byte data from resultSet
                System.out.println(imgData);
                response.setHeader("expires", "0");
                response.setContentType("image/jpg");

                OutputStream os = response.getOutputStream(); // output with the help of outputStream
                os.write(imgData);
                os.flush();
                os.close();*/

            }
        } catch (SQLException | IOException ex) {
            // String message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
