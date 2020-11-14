package packages.businessLayer;

import javax.servlet.http.Part;
import java.awt.Image;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

public class Post {
    private InputStream attachment;
    private String text;
    private Date date;
    private boolean updated;
    private String tags;
    private int postID;
    private int userID;
    private Timestamp lastUpdated;
    private String originalFileName;
    private String fileType;
    private long fileSize;

    public Post(int userID, String text, InputStream file, Date date, String tags, String fileName, long size, String type) {
        this.userID = userID;
        this.text = text;
        this.attachment = file;
        this.date = date;
        this.updated = false;
        this.tags = tags;
        this.originalFileName = fileName;
        this.fileSize = size;
        this.fileType = type;
    }

    public String getOriginalFileName() {
        return this.originalFileName;
    }

    public String getFileType() {
        return this.fileType;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setLastUpdated(Timestamp ts) {
        this.lastUpdated = ts;
    }

    public Timestamp getLastUpdated() {
        return this.lastUpdated;
    }

    public Post(int postID, int userID, String text, InputStream file, Date date, String tags){
        this.postID = postID;
        this.userID = userID;
        this.text = text;
        this.attachment = file;
        this.date = date;
        this.updated = false;
        this.tags = tags;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setAttachment(InputStream att) {
        this.attachment = att;
    }

    public InputStream getAttachment() {
        return this.attachment;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void setUpdated(boolean b) {
        this.updated = true;
    }

    public void setPostID(int id) {
        this.postID = id;
    }

    public int getPostID() {
        return this.postID;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return this.tags;
    }
}
