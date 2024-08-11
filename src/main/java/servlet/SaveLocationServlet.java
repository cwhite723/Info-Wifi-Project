package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static exception.ErrorCode.MISSING_REQUEST_PARAMETER;

@WebServlet("/saveLocationToSession")
public class SaveLocationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        if (latitude == null || longitude == null) ExceptionHandlerServlet.handleException(response, MISSING_REQUEST_PARAMETER);

        try {
            HttpSession session = request.getSession();
            session.setAttribute("latitude", latitude);
            session.setAttribute("longitude", longitude);
            session.setMaxInactiveInterval(60 * 30);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, MISSING_REQUEST_PARAMETER);
        }
    }
}
