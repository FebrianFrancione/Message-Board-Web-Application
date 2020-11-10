package packages.businessLayer;

import packages.DAO.DAO;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    public void createPost(int userID, String text, String att, String tags) {
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

    public String display() {
        String out = "";
        ArrayList<Post> postsList = daoObj.retrievePosts();

        for (Post post: postsList) {
            out += "<div class=\"post\" id=" + post.getUserID() + "><h5 id=" + post.getPostID() + ">" + post.getPostID() + "</h5><h4>" + daoObj.retrieveUsername(post.getUserID()) + "</h4><div class=\"post-body\">" + post.getText() + "</div><div class=\"post-date\">" + post.getDate().toString() + "</div><div class=\"post-tags\">" + post.getTags() + "</div></div>";
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
