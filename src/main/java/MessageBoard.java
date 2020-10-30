import java.awt.Image;
import java.util.Date;

public class MessageBoard {

    private String username;
    private String password;

    //Sign up functionality not to be implemented
    public void signUp(String username, String password) {
        User newUser = new User(username, password);
        /*insert into database*/
    }

    //change password functionality not to be implemented
    public void changePassword(User user, String password) {
        /*find user in database*/
        user.setPassword(password);
    }

    //modify account functionality not to be implemented
    public void modifyAccount(User user, String username, String password) {
        /*find user in database*/
        user.setUsername(username);
        user.setPassword(password);
    }

    public void createPost(User user, String text, Image att, String tags) {
        Date date = new Date();
        String[] tagsArray = tags.split(" ");

        Post post = new Post(user, text, att, date, tagsArray);

        /*insert post into database*/

        displayRecent();
    }

    public void deletePost(User user, Post post) {

        if (user.getUsername() == post.getUser().getUsername()) {
            /*remove post from database*/
            displayRecent();
        }else {
            /*display error*/
        }
    }

    public void updatePost(User user, Post post, String text) {
        /*retrieve post from database*/

        if (user.getUsername() == post.getUser().getUsername()) {
            post.setText(text);
            post.setUpdated(true);
            /*insert into database*/
            displayRecent();
        }else {
            /*display error*/
        }
    }

    public void updatePost(User user, Post post, Image att) {
        /*retrieve post from database*/

        if (user.getUsername() == post.getUser().getUsername()) {
            post.setAttachment(att);
            post.setUpdated(true);
            /*insert into database*/
            displayRecent();
        }else {
            /*display error*/
        }
    }

    public void updatePost(User user, Post post, String text ,Image att) {
        /*retrieve post from database*/

        if (user.getUsername() == post.getUser().getUsername()) {
            post.setText(text);
            post.setAttachment(att);
            post.setUpdated(true);
            /*insert into database*/
            displayRecent();
        }else {
            /*display error*/
        }
    }

    public void search(User user, Date from, Date to, String[] tags) {
        /*retrieve an array from database*/

        Post[] posts = new Post[5];

        display(posts);
    }

    public void displayRecent() {
        /*retreive posts in-order from database*/

        Post[] posts = new Post[10];
        display(posts);
    }

    public void display(Post[] posts) {
        /*print out posts to front end*/
    }
}
