package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.BookmarkGroupDao;
import model.vo.BookmarkGroup;

import java.io.IOException;
import java.util.List;

@WebServlet("/bookmarkGroup")
public class BookmarkGroupServlet extends HttpServlet {
    private final BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                bookmarkGroupDao.deleteGroup(id);
            } else {
                String idParam = request.getParameter("id");
                String groupName = request.getParameter("groupName");
                int sequence = Integer.parseInt(request.getParameter("sequence"));

                if (idParam != null && !idParam.isEmpty()) {
                    int id = Integer.parseInt(idParam);
                    bookmarkGroupDao.updateGroup(id, groupName, sequence);
                } else {
                    bookmarkGroupDao.addGroup(groupName, sequence);
                }
            }
            response.sendRedirect("bookmarkGroup");
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String editId = request.getParameter("editId");

        try {
            if (editId != null) {
                int id = Integer.parseInt(editId);
                BookmarkGroup group = bookmarkGroupDao.getGroupById(id);
                request.setAttribute("group", group);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/view/bookmark-group-edit.jsp");
                dispatcher.forward(request, response);
            } else {
                List<BookmarkGroup> groupList = bookmarkGroupDao.getAllGroups();
                request.setAttribute("groupList", groupList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/view/bookmark-group.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }
}
