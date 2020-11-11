import packages.DAO.DAO;
import packages.businessLayer.MessageBoard;
import packages.businessLayer.Post;
import packages.businessLayer.User;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;


@WebServlet(name="MessageBoardServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file size up yto 16MB
public class MessageBoardServlet extends HttpServlet {
    public void init() throws ServletException {

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userID = (Integer) request.getSession().getAttribute("userID");
        //if we have an active session and userID is set
        if(userID != null){
            // the message board object is our business layer object that is responsible for manipulating the data
            MessageBoard msgBoard = new MessageBoard();

            Part msgBoardAction = null;

            if (request.getPart("create") != null) {
                String text = request.getParameter("message");
                InputStream inputStream = request.getPart("photo").getInputStream();
                String tags = request.getParameter("tags");

                msgBoard.createPost(userID, text, inputStream, tags);
            }

            if (request.getPart("delete") != null) {
                String ID = request.getParameter("deletePost");
                msgBoard.deletePost(userID, Integer.parseInt(ID));
            }

            if (request.getPart("update") != null) {
                String text = request.getParameter("updatedMessage");
                String ID = request.getParameter("updatePost");
                InputStream inputStream = request.getPart("updatedAttachment").getInputStream();
                String tags = request.getParameter("updatedTags");

                msgBoard.updatePost(userID, Integer.parseInt(ID), text, inputStream, tags);
            }

            RequestDispatcher rd = request.getRequestDispatcher("loggedIn.jsp");
            //response.sendRedirect("loggedIn.jsp");
            rd.forward(request, response);

        }else{
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            //rd.include(request, response);
        }



        String action = request.getParameter("action");

        if(action != null && action.equalsIgnoreCase("login")){
            String username =  request.getParameter("username");
            String password = request.getParameter("password");

//            int userID = msgBoard.verifyUser(username,password);
//            if(userID != -1) {
//                request.getSession().setAttribute("userID",userID);
//            }


        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        // enter new user
        //msgBoard.signUp(username,password);

//        int userID = msgBoard.signUp(username,password,email);
//        if(userID != -1){
//            RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
//            dispatcher.forward(request,response);
//        }


//        Post post1 = new Post(request.,request.getParameter("postID"),request.getParameter("text"),request.getParameter("tags"),request.getParameter("date"),request.getParameter("atachment"));
        //press on the create button to test out the different methods.
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        dispatcher.forward(request,response);
    }
}
