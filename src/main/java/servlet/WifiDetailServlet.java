package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.BookmarkGroupDao;
import model.dao.WifiDao;
import model.vo.BookmarkGroup;
import model.vo.Wifi;
import model.vo.WifiDistance;

import java.io.IOException;
import java.util.List;

import static exception.ErrorCode.LOCATION_NOT_EXISTS;

@WebServlet("/wifiDetail")
public class WifiDetailServlet extends HttpServlet {
    private WifiDao wifiDao = new WifiDao();
    private BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int wifiId = Integer.parseInt(request.getParameter("wifiId"));
        Wifi wifi = wifiDao.getWifiById(wifiId);

        try {
            HttpSession session = request.getSession();
            Object latitudeObj = session.getAttribute("latitude");
            Object longitudeObj = session.getAttribute("longitude");

            if (latitudeObj == null || longitudeObj == null)
                ExceptionHandlerServlet.handleException(response, LOCATION_NOT_EXISTS);
            double latitude = Double.parseDouble(latitudeObj.toString());
            double longitude = Double.parseDouble(longitudeObj.toString());

            WifiDistance wifiDistance = new WifiDistance(wifi, wifi.calculateDistance(latitude, longitude));
            List<BookmarkGroup> groupList = bookmarkGroupDao.getAllGroups();

            request.setAttribute("wifiDetail", wifiDistance);
            request.setAttribute("groupList", groupList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/wifi-detail.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e){
            ExceptionHandlerServlet.handleException(response, e);
        }
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int wifiId = Integer.parseInt(request.getParameter("wifiId"));
    int groupId = Integer.parseInt(request.getParameter("groupId"));

    try {
        bookmarkGroupDao.addBookmarkToGroup(groupId, wifiId);
        response.sendRedirect(request.getContextPath() + "/bookmark");
    } catch (Exception e) {
        ExceptionHandlerServlet.handleException(response, e);
    }
}
}
