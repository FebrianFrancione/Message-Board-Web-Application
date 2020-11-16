
import packages.DAO.DAO;
import packages.businessLayer.MessageBoard;
import packages.businessLayer.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> listPost = null;
        MessageBoard msg = new MessageBoard();

        try {
            listPost = msg.listPost(getServletContext(), "false");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("listPost", listPost);
        RequestDispatcher dispatcher = request.getRequestDispatcher("postList.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        List<Post> listBook = null;
//        try {
//            DAO dao = new DAO();
//            listBook = dao.listAllBooks();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        request.setAttribute("listBook", listBook);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("postList.jsp");
//        dispatcher.forward(request, response);
//
//    }
    }
}
