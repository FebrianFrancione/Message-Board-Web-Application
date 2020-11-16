package packages.businessLayer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;import packages.DAO.DAO;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import packages.database.DBConnection;


public class MessageBoard {
    //Our Data Access Object, this is responsible inserting, updating, deleting, retrieving from the database
    private DAO daoObj = new DAO();


    //    public void checkUserID(String username,String password){
//        daoObj.retrieveUserID(username,password);
//    }
    //Sign up functionality not to be implemented
//    public void signUp(String username, String password) {
//        User newUser = new User(username, password);
//
//        /*insert into database*/
//        daoObj.insert(newUser);
//    }

    //modify account functionality not to be implemented
//    public void modifyAccount(User user, String username, String password, String email) {
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        /*update user in database*/
//        daoObj.update(user);
//    }

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

    public ArrayList<Post> searchCases(String option, String username, String fromDate, String toDate, String tag, ServletContext context) {

        if (option instanceof String) {
            switch (option) {
                case "searchUser":
                    return daoObj.searchPosts(username, context);
                case "searchByDate":
                    return daoObj.searchByDates(fromDate, toDate);
                case "searchByTags":
                    return daoObj.searchByTags(tag);
                case "searchAll":
                    return daoObj.searchAll(username, fromDate, toDate, tag, context);
            }
        }
        return null;
    }

    // display searched items
    public List<Post> search(String option, String username, String fromDate, String toDate, String tag, int i, String reverse, ServletContext context) throws IOException, SQLException {
        System.out.println(option + " " + username +" " + fromDate +" " + toDate +" " + tag +" " + i +" " + reverse+ " " +context);
        String out = "";

        if (option == null) {
            if(reverse == null) {
                //out = display(i, "true", context);
                return listPost(context, "true");
            }
            else{
                //out = display(i, reverse, context);
                return listPost(context, reverse);
            }
        }
        if (option != null && username == null  && fromDate == null && toDate == null && tag == null){
            if(reverse == null){
                //out = display(i, "true", context);
                return listPost(context, "true");
            }
            else{
                //out = display(i, reverse, context);
                return listPost(context, reverse);
            }
        }

        if (fromDate == null && toDate != null){
            if(option != null && reverse == null){
                //out = display(i, "true", context);
                return listPost(context, "true");
            }else if (option != null && reverse != null){
                //out = display(i, reverse, context);
                return listPost(context, reverse);
            }
        }
        if (fromDate != null && toDate == null){}

        ArrayList<Post> searchedAllPosts = searchCases(option, username, fromDate, toDate, tag, context);
        ArrayList<Post> filesSearchedAllPosts = daoObj.retrieveFiles(searchedAllPosts);

        if (searchedAllPosts != null) {
            if (reverse.equals("true")) {
                Collections.reverse(filesSearchedAllPosts);
            }
        }

        for (Post post: searchedAllPosts) {
            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int) post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                post.setImageString(imageString);
            }
        }

        return searchedAllPosts;
    }


    public List<Post> listPost(ServletContext context, String reversed) throws SQLException, IOException {
        List<Post> listPost = null;
        listPost = daoObj.listAllPosts();
        listPost = daoObj.retrieveAllFiles(listPost);

        if (reversed.equals("true")) {
            Collections.reverse(listPost);
        }

        for (Post post: listPost) {
            post.setUsername(daoObj.retrieveUsername(post.getUserID(), context));

            if (post.getAttachment() != null) {
                byte[] imageBytes = new byte[(int) post.getAttachment().available()];
                post.getAttachment().read(imageBytes, 0, imageBytes.length);
                String imageString = Base64.encodeBase64String(imageBytes);
                post.setImageString(imageString);
            }
        }
        return listPost;
    }


    public ArrayList<Integer> retrievePostIDs() {
        ArrayList<Integer> IDs = new ArrayList<Integer>();
        for (Post post : daoObj.retrievePosts()) {
            IDs.add(post.getPostID());
        }
        return IDs;
    }

    public int verifyUser(String username, String password, ServletContext context) throws FileNotFoundException {

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

        return daoObj.verifyUser(username, hashedpass, context);
    }

    public void download(String fileID, ServletContext context, HttpServletResponse response, int BufferSize) {
        Connection conn = null;
        Statement stmt = null;
        String fileName = null;
        Blob blob;

        try {

            Object[] fileData = daoObj.getFileInfo(fileID);


            if (fileData != null) {
                // get the file name
                fileName = (String) fileData[2];
                //get the blob
                blob = (Blob) fileData[0];

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
