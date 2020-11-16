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

@WebServlet(name = "SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageBoard msgBoard = new MessageBoard();
        List<Post> listPost = null;

        String username = request.getParameter("searchUsername");
        String dateRangeFrom = request.getParameter("fromDate");
        String dateRangeTo = request.getParameter("toDate");
        String tag = request.getParameter("searchByTags");
        String option = request.getParameter("searchOption");
        String dateSort = request.getParameter("dateSorting");

        try {
            listPost = msgBoard.search(option, username, dateRangeFrom, dateRangeTo, tag, 100, dateSort, getServletContext());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        request.setAttribute("context", getServletContext());
        request.setAttribute("option", option);
        request.setAttribute("username", username);
        request.setAttribute("dateRangeFrom", dateRangeFrom);
        request.setAttribute("dateRangeTo", dateRangeTo);
        request.setAttribute("tag", tag);
        request.setAttribute("dateSort", dateSort);
        request.setAttribute("listPost", listPost);
        RequestDispatcher rd = request.getRequestDispatcher("searchPage.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
