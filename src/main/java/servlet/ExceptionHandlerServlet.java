package servlet;

import exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static exception.ErrorCode.UNEXPECTED_ERROR;

public class ExceptionHandlerServlet{
    public static void handleException(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.sendError(errorCode.getCode(), errorCode.getMessage());
    }

    public static void handleException(HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR.getMessage());
    }
}
