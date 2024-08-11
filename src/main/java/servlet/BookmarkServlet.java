package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookmarkDao;
import model.vo.Bookmark;

import java.io.IOException;
import java.util.List;

@WebServlet("/bookmark")
public class BookmarkServlet extends HttpServlet {
    private BookmarkDao bookmarkDao = new BookmarkDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                int wifiId = Integer.parseInt(request.getParameter("wifiId"));
                int groupId = Integer.parseInt(request.getParameter("groupId"));
                bookmarkDao.addBookmark(wifiId, groupId);
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                bookmarkDao.deleteBookmark(id);
            }
            response.sendRedirect("bookmark");
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Bookmark> bookmarkList = bookmarkDao.getAllBookmarks();
            request.setAttribute("bookmarkList", bookmarkList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/bookmark.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }
}
