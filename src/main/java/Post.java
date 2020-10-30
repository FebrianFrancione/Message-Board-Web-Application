import java.awt.Image;
import java.util.Date;

public class Post {
    private Image attachment;
    private String text;
    private Date date;
    private User user;
    private boolean updated;
    private String[] tags;

    public Post(User user, String text, Image img, Date date, String[] tags) {
        this.user = user;
        this.text = text;
        this.attachment = img;
        this.date = date;
        this.updated = false;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setAttachment(Image att) {
        this.attachment = att;
    }

    public User getUser() {
        return this.user;
    }

    public void setUpdated(boolean b) {
        this.updated = true;
    }
}
