import com.sun.glass.ui.Size;

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
    private static final int BUFFER_SIZE = 4096;

    // database connection settings
    private String dbURL = "jdbc:mysql://127.0.0.1:3306/messageboard?user=root";
    private String dbUser = "root";
    private String dbPass = "1234";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String dbURL = "jdbc:mysql://127.0.0.1:3306/messageboard?user=root";
        final String dbUser = "root";
        final String dbPass = "1234";

        Connection conn = null;
        Statement stmt = null;

        try {
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            System.out.println("db connected");
            stmt = (Statement) conn.createStatement();

            ResultSet rs1;
            rs1 = stmt.executeQuery("select file from files where fileID = 1");

            if (rs1.next()) {
                byte[] imgData = rs1.getBytes("file");//Here....... r1.getBytes() extract byte data from resultSet
                System.out.println(imgData);
                response.setHeader("expires", "0");
                response.setContentType("image/jpg");

                OutputStream os = response.getOutputStream(); // output with the help of outputStream
                os.write(imgData);
                os.flush();
                os.close();

            }
        } catch (SQLException | ClassNotFoundException ex) {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//        }
    }
}

