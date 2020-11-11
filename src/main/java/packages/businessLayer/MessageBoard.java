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
import java.sql.Timestamp;
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

    public void createPost(int userID, String text, InputStream att, String tags) throws IOException {
        Date date = new Timestamp(System.currentTimeMillis());
        Post post = new Post(userID, text, att, date, tags);

        daoObj.insert(post);
    }

    public void deletePost(int userID, int postID) {
        daoObj.deletePost(userID ,postID);
    }

    public void updatePost(int userID, int postID, String text, InputStream inputStream, String tags) {
        daoObj.update(userID, postID, text, inputStream, tags);
    }

    public void search(User user, Date from, Date to, String[] tags) {
        /*retrieve an array from database*/
    }

    public void displayRecent() {
        /*retreive posts in-order from database*/
    }

    public String display() throws IOException {
        String out = "";
        ArrayList<Post> postsWithoutFiles = daoObj.retrievePosts();
        ArrayList<Post> postsList = daoObj.retrieveFiles(postsWithoutFiles);
        int counter = 0;
        for (Post post: postsList) {
            out += "" +
                    "<div class=\"post\" style=\"margin: 0 20px; padding: 10px;\" id=" + post.getUserID() + ">" +
                        "<div class=\"post-ids\" style=\"display: flex; flex-direction: row; justify-content: space-between;\">"+
                            "<div>Username: " + daoObj.retrieveUsername(post.getUserID()) + "</div>" +
                            "<div>Post id: " + post.getPostID() + "</div>" +
                        "</div>"+
                        "<div class=\"post-body\" style=\"font-size: 20px; margin-top: 20px;\">" + post.getText() + "</div>" +
                        "<div class=\"post-tags\" style=\"margin-top: 20px; color: grey;\">" + post.getTags() + "</div>" +
                        "<div class=\"post-date\" style=\"margin-top: 10px; font-size: 12px;\">" + post.getDate().toString() + "</div>";

            BufferedImage image = null;
            if (post.getAttachment() != null)
                image = ImageIO.read(post.getAttachment());
            if (image != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", bos );
                byte [] data = bos.toByteArray();
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                BufferedImage bImage2 = ImageIO.read(bis);
                String fileName = "test" + counter +".png";
                ImageIO.write(bImage2, "png", new File("C:\\Users\\alway\\Desktop\\SOEN 387\\SOEN-387-A2\\src\\main\\webapp\\files\\"+fileName) );  //paste your absolute url path here and add \\ after it
                out += "<div style=\"margin-top: 15px;\"><img style=\"width:200px; height: 250px;\" src=\"./files/"+fileName+"\"></div></div>";
                System.out.println("image created");
                counter++;
            }else {
                out += "</div>";
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
