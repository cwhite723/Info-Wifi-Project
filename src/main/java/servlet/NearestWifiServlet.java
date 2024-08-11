package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.WifiDao;
import model.vo.WifiDistance;
import model.vo.Wifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/getNearestWifi")
public class NearestWifiServlet extends HttpServlet {
    private WifiDao wifiDao = new WifiDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            double userLatitude = Double.parseDouble(request.getParameter("latitude"));
            double userLongitude = Double.parseDouble(request.getParameter("longitude"));
            getNearestWifi(request, response, userLatitude, userLongitude);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    private void getNearestWifi(HttpServletRequest request, HttpServletResponse response, double userLatitude, double userLongitude) throws ServletException, IOException {
        try {
            List<Wifi> wifiList = wifiDao.findAll();
            List<WifiDistance> wifiDistanceList = calculateDistances(wifiList, userLatitude, userLongitude);

            List<WifiDistance> nearestWifiList = wifiDistanceList.stream()
                    .sorted((w1, w2) -> Double.compare(w1.getDistance(), w2.getDistance()))
                    .limit(20)
                    .collect(Collectors.toList());

            request.setAttribute("wifiList", nearestWifiList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/index.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    private List<WifiDistance> calculateDistances(List<Wifi> wifiList, double userLatitude, double userLongitude) {
        List<WifiDistance> wifiDistanceList = new ArrayList<>();

        for (Wifi wifi : wifiList) {
            double distance = wifi.calculateDistance(userLatitude, userLongitude);
            WifiDistance wifiDistance = new WifiDistance(wifi, distance);
            wifiDistanceList.add(wifiDistance);
        }

        return wifiDistanceList;
    }
}
