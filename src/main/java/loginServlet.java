import packages.businessLayer.MessageBoard;
import packages.businessLayer.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "loginServlet")
public class loginServlet extends HttpServlet {
//    private final String username = "admin"; //hardcoded for now
//    private final String password = "password"; //hardcoded for now
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        MessageBoard mObj = new MessageBoard();
        ServletContext context = getServletContext();
        List<Post> listPost = null;
        int userID = mObj.verifyUser(username,password, context);

        if(userID != -1){
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //generate a new session
            HttpSession newSession = request.getSession(true);

            //setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);

            newSession.setAttribute("userID",userID);
            //

            try {
                listPost = mObj.listPost(getServletContext(), "false");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("listPost", listPost);
            request.getSession().setAttribute("loggedInUser", userID);
            request.setAttribute("context", context);
            Cookie message = new Cookie("message", "Welcome");
            response.addCookie(message);
            //response.sendRedirect("loggedIn.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("loggedIn.jsp");
            rd.forward(request,response);

        }else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/main.jsp");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            rd.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
