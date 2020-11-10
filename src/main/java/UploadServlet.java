import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UploadServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file size up yto 16MB
public class UploadServlet extends HttpServlet {

    // database connection settings
    private String dbURL = "jdbc:mysql://localhost:3306/messageboard";
    private String dbUser = "root";
    private String dbPass = "1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = null; // input stream of the upload file

// obtains the upload file part in this multipart request
        Part filePart = request.getPart("photo");
        if (filePart != null) {
// prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

// obtains input stream of the upload file
            inputStream = filePart.getInputStream();

        }

        Connection conn = null; // connection to the database
        String message = null; // message will be sent back to client
        Timestamp date = new Timestamp(System.currentTimeMillis());
        try{
// connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

// constructs SQL statement
            String sql = "INSERT INTO files (file,date) values (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            if(inputStream != null){
// fetches input stream of the upload file for the blob column
                statement.setBlob(1, inputStream);
            }
// statement.setString(1, file);
            statement.setTimestamp(2, date);
// sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }

        } catch (SQLException e) {
            message = "Error: " + e.getMessage();
            e.printStackTrace();
        } finally{
            if(conn!= null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
// sets the message in request scope
                request.setAttribute("uploaded_message", message);

// forwards to the message page
                getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);

            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}