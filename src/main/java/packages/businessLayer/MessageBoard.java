package packages.businessLayer;

import packages.DAO.DAO;

import javax.imageio.ImageIO;
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
import java.sql.Array;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;


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
        daoObj.deletePost(userID ,postID);
    }

    public void updatePost(int userID, int postID, String text, InputStream inputStream, String tags) {
        daoObj.update(userID, postID, text, inputStream, tags);
    }

//    public void search(User user, Date from, Date to, String[] tags) {
//        /*retrieve an array from database*/
//    }

    public String displaySearched(String username, int i, String reverse) throws IOException{
        String out = "";
        ArrayList<Post> searchedPosts = daoObj.searchPosts(username);
        ArrayList<Post> filesSearchedPosts = daoObj.retrieveFiles(searchedPosts);
        String lastUpdated = "";

        if (reverse.equals("true")) {
            Collections.reverse(filesSearchedPosts);
        }

        int counter = 0;
        for (Post post: filesSearchedPosts) {
            if (counter == i) {
                break;
            }
            lastUpdated = "";
            if (post.getLastUpdated() != null) {
                lastUpdated += "Last Updated: " + post.getLastUpdated();
            }

            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                    "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">"+
                    "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                    "<div>Post id: " + post.getPostID() + "</div>" +
                    "</div>"+
                    "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                    "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                    "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

            BufferedImage image = null;

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int)post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
            }else {
                out += "</div>";
            }
            counter ++;
        }
        return out;
    }

    public String displaySearchByDates(String date, int i, String reverse) throws IOException{
        String out = "";
        ArrayList<Post> searchedByDates = daoObj.searchByDates(date);
        ArrayList<Post> filesSearchedPosts = daoObj.retrieveFiles(searchedByDates);
        String lastUpdated = "";

        if (reverse.equals("true")) {
            Collections.reverse(filesSearchedPosts);
        }

        int counter = 0;
        for (Post post: filesSearchedPosts) {
            if (counter == i) {
                break;
            }
            lastUpdated = "";
            if (post.getLastUpdated() != null) {
                lastUpdated += "Last Updated: " + post.getLastUpdated();
            }

            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                    "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">"+
                    "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                    "<div>Post id: " + post.getPostID() + "</div>" +
                    "</div>"+
                    "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                    "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                    "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

            BufferedImage image = null;

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int)post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
            }else {
                out += "</div>";
            }
            counter ++;
        }
        return out;
    }

    public String displaySearchByTags(String tag, int i, String reverse) throws IOException{
        String out = "";
        ArrayList<Post> searchedByTags = daoObj.searchByTags(tag);
        ArrayList<Post> filesSearchedPosts = daoObj.retrieveFiles(searchedByTags);
        String lastUpdated = "";

        if (reverse.equals("true")) {
            Collections.reverse(filesSearchedPosts);
        }

        int counter = 0;
        for (Post post: filesSearchedPosts) {
            if (counter == i) {
                break;
            }
            lastUpdated = "";
            if (post.getLastUpdated() != null) {
                lastUpdated += "Last Updated: " + post.getLastUpdated();
            }

            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                    "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">"+
                    "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                    "<div>Post id: " + post.getPostID() + "</div>" +
                    "</div>"+
                    "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                    "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                    "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

            BufferedImage image = null;

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int)post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
            }else {
                out += "</div>";
            }
            counter ++;
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
        for (Post post: postsList) {
            if (counter == i) {
                break;
            }
            lastUpdated = "";
            if (post.getLastUpdated() != null) {
                lastUpdated += "Last Updated: " + post.getLastUpdated();
            }

            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                        "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">"+
                            "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                            "<div>Post id: " + post.getPostID() + "</div>" +
                        "</div>"+
                        "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                        "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                        "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "<br>" + lastUpdated + "</div>";

            BufferedImage image = null;

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int)post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"data:image/jpeg;base64, " + imageString + "\"></div></div>";
            }else {
                out += "</div>";
            }
            counter ++;
        }
        return out;
    }

    public ArrayList<Integer> retrievePostIDs() {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for (Post post: daoObj.retrievePosts()) {
            IDs.add(post.getPostID());
        }
        return IDs;
    }

    public int verifyUser(String username, String password){

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
        User temp = new User(username,hashedpass);

        User returned = daoObj.verifyPassword(temp);

        if (returned != null){
            return returned.getUserID();
        }
        return -1;
    }
}
