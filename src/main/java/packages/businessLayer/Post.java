package packages.businessLayer;

import javax.servlet.http.Part;
import java.awt.Image;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private String userID1;
    private ZonedDateTime date1;
    private String dateString;
    private String imageString;
    private String username;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public ZonedDateTime getDate1() {
        return date1;
    }

    public void setDate1(ZonedDateTime date1) {
        this.date1 = date1;
    }

    private String attachmentSource;

    //used for jstl date time formatter is not functioning
    public Post(int postID, String userID1, String text, String attachmentSource, Timestamp postedDate, String tags, Timestamp lastUpdated){
        this.postID = postID;
        this.userID1 = userID1;
        this.text = text;
        this.attachmentSource = attachmentSource;
//        date1 = ZonedDateTime.parse(postedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        dateString = date1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateString = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(postedDate);
        this.date = postedDate;
        this.tags = tags;
        this.lastUpdated = lastUpdated;
    }

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


    public Post(int postID, int userID, String text, InputStream file, Date date, String tags){
        this.postID = postID;
        this.userID = userID;
        this.text = text;
        this.attachment = file;
        this.date = date;
        this.updated = false;
        this.tags = tags;
    }


    public String getUserID1() {
        return userID1;
    }

    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }

    public String getAttachmentSource() {
        return attachmentSource;
    }

    public void setAttachmentSource(String attachmentSource) {
        this.attachmentSource = attachmentSource;
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

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImageString() {
        return this.imageString;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
