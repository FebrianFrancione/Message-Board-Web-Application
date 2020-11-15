import com.sun.glass.ui.Size;
import packages.DAO.DAO;
import packages.database.DBConnection;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet {

    // size of byte buffer to send file
    private static final int BUFFER_SIZE =1024*10;

    // database connection settings
    private String dbURL = "jdbc:mysql://127.0.0.1:3306/messageboard?user=root";
    private String dbUser = "root";
    private String dbPass = "1234";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get the fileID of the file requested from file_list.jsp
        String fileID = request.getParameter("fileID");

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
            ResultSet rs1 = stmt.executeQuery("select file,fileName from files where fileID = " + 20);

            if (rs1.next()) {
                // get the file name
                fileName = rs1.getString("fileName");
                //get the blob
                blob = rs1.getBlob("file");

                InputStream inputStream = blob.getBinaryStream();

                ServletContext context = getServletContext();

                // find the mimeType of the file . Set binary if nothing is found
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {
                    mimeType = "application/binary";
                }

                // setting the Content type header, the content length header, and the Content disposition header
                response.setContentType(mimeType);
                response.setContentLength(inputStream.available());     //inputStream.available() will give the file length
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader("Content-Disposition" , headerValue);

                // get the servlet output stream
                OutputStream outStream = response.getOutputStream();

                //buffer size is defined at the top
                byte[] buffer = new byte[BUFFER_SIZE];
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
        } catch (SQLException ex) {
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
/*
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int postID = Integer.parseInt(request.getParameter("postId"));
            int attachmentID = Integer.parseInt(request.getParameter("attachmentId"));

            DAO upd = new DAO();
            String fileName = upd.getFileName(postID, attachmentID);
            response.setContentType(upd.getContentType(postID, attachmentID));
            response.setHeader("Content-Disposition", "attachment; fileName= " + fileName);

            OutputStream out = response.getOutputStream();

            upd.downloadFile(postID, attachmentID, fileName, out);


        }*/

//        Blob image = null;
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        ServletOutputStream out = response.getOutputStream();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/messageboard?user=root","root","1234");
//            stmt = con.createStatement();
//            rs = stmt.executeQuery("select file from files where fileID = '1'");
//            if (rs.next()) {
//                image = rs.getBlob(1);
//            } else {
//                response.setContentType("text/html");
//                out.println("<html><head><title>Display Blob Example</title></head>");
//                out.println("<body><h4><font color='red'>image not found for given id</font></h4></body></html>");
//                return;
//            }
//            response.setContentType("image/gif");
//            InputStream in = image.getBinaryStream();
//            int length = (int) image.length();
//            int bufferSize = 1024;
//            byte[] buffer = new byte[bufferSize];
//            while ((length = in.read(buffer)) != -1) {
//                out.write(buffer, 0, length);
//            }
//            in.close();
//            out.flush();
//        } catch (Exception e) {
//            response.setContentType("text/html");
//            out.println("<html><head><title>Unable To Display image</title></head>");
//            out.println("<body><h4><font color='red'>Image Display Error=" + e.getMessage() +
//                    "</font></h4></body></html>");
//            return;
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
}

