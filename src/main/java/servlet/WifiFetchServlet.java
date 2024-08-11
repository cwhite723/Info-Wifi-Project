package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.WifiDao;
import model.vo.Wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fetchWifiData")
public class WifiFetchServlet extends HttpServlet {
    private final String baseUrl = "http://openapi.seoul.go.kr:8088/524143447163776832394151454e64/json/TbPublicWifiInfo/";
    private WifiDao wifiDao = new WifiDao();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            fetchDataFromApi(request, response);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    private void fetchDataFromApi(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String firstPageUrl = baseUrl + "1/1";
            JsonObject firstPageResponse = getJsonObjectFromApi(firstPageUrl);

            JsonObject tbPublicWifiInfo = firstPageResponse.getAsJsonObject("TbPublicWifiInfo");
            int totalCount = tbPublicWifiInfo.get("list_total_count").getAsInt();

            int pageSize = 1000;
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            int savedCount = 0;

            for (int page = 1; page <= totalPages; page++) {
                String apiUrl = baseUrl + String.format("%d/%d", (page - 1) * pageSize + 1, page * pageSize);
                List<Wifi> pageData = getWifiData(apiUrl);
                for (Wifi wifi : pageData) {
                    wifiDao.save(wifi);
                    savedCount++;
                }
            }

            request.setAttribute("savedCount", savedCount);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/load-wifi.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            ExceptionHandlerServlet.handleException(response, e);
        }
    }

    private List<Wifi> getWifiData(String apiUrl) throws IOException {
        JsonObject jsonObject = getJsonObjectFromApi(apiUrl);
        JsonObject tbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
        List<Wifi> wifiList = new ArrayList<>();

        if (tbPublicWifiInfo != null) {
            JsonArray wifiArray = tbPublicWifiInfo.getAsJsonArray("row");
            for (int i = 0; i < wifiArray.size(); i++) {
                JsonObject wifiObject = wifiArray.get(i).getAsJsonObject();
                Wifi wifi = new Wifi();
                wifi.setLatitude(Double.parseDouble(wifiObject.get("LAT").getAsString()));
                wifi.setLongitude(Double.parseDouble(wifiObject.get("LNT").getAsString()));
                wifi.setManagementNumber(wifiObject.get("X_SWIFI_MGR_NO").getAsString());
                wifi.setCity(wifiObject.get("X_SWIFI_WRDOFC").getAsString());
                wifi.setWifiName(wifiObject.get("X_SWIFI_MAIN_NM").getAsString());
                wifi.setRoadAddress(wifiObject.get("X_SWIFI_ADRES1").getAsString());
                wifi.setSubAddress(wifiObject.get("X_SWIFI_ADRES2").getAsString());
                wifi.setInstallFloor(wifiObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                wifi.setInstallType(wifiObject.get("X_SWIFI_INSTL_TY").getAsString());
                wifi.setInstallAgency(wifiObject.get("X_SWIFI_INSTL_MBY").getAsString());
                wifi.setServiceType(wifiObject.get("X_SWIFI_SVC_SE").getAsString());
                wifi.setNetworkType(wifiObject.get("X_SWIFI_CMCWR").getAsString());
                wifi.setInstallYear(wifiObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                wifi.setLocationType(wifiObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                wifi.setConnectionEnvironment(wifiObject.get("X_SWIFI_REMARS3").getAsString());

                try {
                    wifi.setCreatedAt(new Timestamp(dateTimeFormat.parse(wifiObject.get("WORK_DTTM").getAsString()).getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (wifi.getLatitude() != 0 && wifi.getLongitude() != 0) wifiList.add(wifi);
            }
        }
        return wifiList;
    }

    private JsonObject getJsonObjectFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("HTTP error code : " + responseCode);
            System.out.println("Error response from API: " + getErrorResponse(conn));
            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        try {
            JsonReader jsonReader = new JsonReader(new StringReader(sb.toString()));
            jsonReader.setLenient(true);
            return JsonParser.parseReader(jsonReader).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.out.println("Error parsing JSON response: " + sb.toString());
            return null;
        }
    }

    private String getErrorResponse(HttpURLConnection conn) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                errorResponse.append(line);
            }
            br.close();
            return errorResponse.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading error response";
        }
    }
}
