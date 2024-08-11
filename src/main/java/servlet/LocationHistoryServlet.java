package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.LocationHistoryDao;
import model.vo.LocationHistory;

import java.io.IOException;
import java.util.List;

@WebServlet("/locationHistory")
public class LocationHistoryServlet extends HttpServlet {
    private LocationHistoryDao locationHistoryDao = new LocationHistoryDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                locationHistoryDao.deleteLocationHistory(id);
            } else {
                double latitude = Double.parseDouble(request.getParameter("latitude"));
                double longitude = Double.parseDouble(request.getParameter("longitude"));
                locationHistoryDao.saveLocationHistory(latitude, longitude);
            }
            response.sendRedirect("locationHistory");
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<LocationHistory> locationHistoryList = locationHistoryDao.getLocationHistory();
            request.setAttribute("locationHistoryList", locationHistoryList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/location-history.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }
}
