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
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

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

    public void createPost(int userID, String text, InputStream att, String tags) {
        Date date = new Date();
        String[] tagsArray = tags.split(" ");

        Post post = new Post(userID, text, att, date, tagsArray);

        daoObj.insert(post);
    }

    public void deletePost(int userID, int postID) {
        daoObj.delete(userID ,postID);
    }

    public void updatePost(int userID, int postID, String text) {
        daoObj.update(userID, postID, text);
    }
//
//    public void updatePost(User user, Post post, String att) {
//        /*retrieve post from database*/
//
//        if (user.getUsername() == post.getUser().getUsername()) {
//            post.setAttachment(att);
//            post.setUpdated(true);
//            /*insert into database*/
//            displayRecent();
//        }else {
//            /*display error*/
//        }
//    }

//    public void updatePost(User user, Post post, String text ,String att, String tags) {
//        if (user.getUserID() == post.getUserID()) {
//            post.setText(text);
//            post.setAttachment(att);
//            post.setTags(tags.split(" "));
//            post.setUpdated(true);
//
//            daoObj.update(post);
//        }else {
//            /*display error*/
//        }
//    }

    public void search(User user, Date from, Date to, String[] tags) {
        /*retrieve an array from database*/
    }

    public void displayRecent() {
        /*retreive posts in-order from database*/
    }

    public String display() throws IOException {
        String out = "";
        ArrayList<Post> postsList = daoObj.retrievePosts();
        int counter = 0;
        for (Post post: postsList) {
            out += "<div class=\"post\" id=" + post.getUserID() + "><h5 id=" + post.getPostID() + ">" + post.getPostID() + "</h5><h4>" + daoObj.retrieveUsername(post.getUserID()) + "</h4><div class=\"post-body\">" + post.getText() + "</div><div class=\"post-date\">" + post.getDate().toString() + "</div><div class=\"post-tags\">" + post.getTags() + "</div></div>";

            BufferedImage image = ImageIO.read(post.getAttachment());
            if (image != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", bos );
                byte [] data = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                BufferedImage bImage2 = ImageIO.read(bis);
                String fileName = "test" + counter +".png";
                ImageIO.write(bImage2, "png", new File("C:\\Users\\alway\\Desktop\\SOEN 387\\SOEN-387-A2\\src\\main\\webapp\\files\\"+fileName) );  //paste your absolute url path here and add \\ after it
                out += "<div><img style=\"width:200px; height: 250px;\" src=\"./files/"+fileName+"\"></div>";
                System.out.println("image created");
                counter++;
            }
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
