import packages.DAO.DAO;
import packages.businessLayer.MessageBoard;
import packages.businessLayer.Post;
import packages.businessLayer.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;



@WebServlet(name="MessageBoardServlet")
public class MessageBoardServlet extends HttpServlet {
    public void init() throws ServletException {

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // the message board object is our business layer object that is responsible for manipulating the data
        MessageBoard msgBoard = new MessageBoard();

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
        msgBoard.signUp(username,password);

//        int userID = msgBoard.signUp(username,password,email);
//        if(userID != -1){
//            RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
//            dispatcher.forward(request,response);
//        }


//        Post post1 = new Post(request.,request.getParameter("postID"),request.getParameter("text"),request.getParameter("tags"),request.getParameter("date"),request.getParameter("atachment"));
        //press on the create button to test out the different methods.

        //mock data this data is already in the database. Defining it here again to make testing easier. To test out functionality of database, refresh the table after running the web application :)
//        User user1 = new User("user1", "12345");
//        user1.setUserID(1); //The userID is not a parameter in the constructor because the database is responsible for generating it. Setting the userID is handled in the DAO class when a new user is created and inserted into the DB
//        User user2 = new User("user2", "123456");
//        user2.setUserID(2);
//        User user3 = new User("user3", "1234567");
//        user3.setUserID(3);

//        Post post1 = new Post(1, "one", "path1", new Date(), new String[] {"#one"});
//        post1.setPostID(1); //same case as userID
//        Post post2 = new Post(1, "two", "path2", new Date(), new String[] {"#two"});
//        post2.setPostID(2);
//        Post post3 = new Post(1, "three", "path3", new Date(), new String[] {"#three"});
//        post3.setPostID(3);
//        Post post4 = new Post(2, "four", "path4", new Date(), new String[] {"#four"});
//        post4.setPostID(4);
//        Post post5 = new Post(2, "five", "path5", new Date(), new String[] {"#five"});
//        post5.setPostID(5);
//        Post post6 = new Post(3, "modify me", "pathModify", new Date(), new String[] {"#modify"});
//        post6.setPostID(6);
//        Post post7 = new Post(3, "deleteMe", "pathDelete", new Date(), new String[] {"#delete"});
//        post7.setPostID(7);

        //1. Creating a post

        User user1 = new User("febrian", "controller");
        user1.getUserID();
//        msgBoard.createPost(user1, "new post", "new attachment path", "#new");

        //2. Delete a post
            //msgBoard.deletePost(user3, post7);

        //3. Update a post
            //msgBoard.updatePost(user3, post6, "modified", "modifiedDone", "#modifiedDone");

        //4. display posts. For now it displays 6 rows, this is hardcoded but will change in the future
            //msgBoard.display();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        dispatcher.forward(request,response);
    }
}
