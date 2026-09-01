package controller;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import model.AppointmentDAO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.ResultSet;

public class DentistWebService {
    
    private HttpServer server;

    public void startServer(String loggedInDentist) {
        try {
            // Create a server listening on http://localhost:8080
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Create a web route called "/schedule"
            server.createContext("/schedule", new ScheduleHandler(loggedInDentist));
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Web Service started on port 8080");
            
        } catch (IOException e) {
            System.out.println("Error starting web service: " + e.getMessage());
        }
    }

    static class ScheduleHandler implements HttpHandler {
        private String dentistName;

        public ScheduleHandler(String dentistName) {
            this.dentistName = dentistName;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>My Schedule</title>");
            html.append("<style>");
            html.append("body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 40px; text-align: center; }");
            html.append("table { width: 80%; margin: 20px auto; border-collapse: collapse; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            html.append("th, td { padding: 15px; border-bottom: 1px solid #ddd; text-align: left; }");
            html.append("th { background-color: #2c3e50; color: white; }");
            html.append("</style></head><body>");
            html.append("<h2>Live Appointment Schedule for ").append(dentistName).append("</h2>");
            html.append("<table><tr><th>ID</th><th>Date & Time</th><th>Patient</th><th>Contact</th><th>Treatment</th></tr>");

            // 2. Fetch live data from the database
            AppointmentDAO dao = new AppointmentDAO();
            ResultSet rs = dao.getAppointmentsByDentist(dentistName);

            try {
                if (rs != null) {
                    while (rs.next()) {
                        html.append("<tr>");
                        html.append("<td>").append(rs.getInt("id")).append("</td>");
                        html.append("<td>").append(rs.getString("appointment_date")).append("</td>"); // Make sure this column matches your DB!
                        html.append("<td>").append(rs.getString("patient_name")).append("</td>");
                        html.append("<td>").append(rs.getString("contact_number")).append("</td>");
                        html.append("<td>").append(rs.getString("treatment")).append("</td>");
                        html.append("</tr>");
                    }
                }
            } catch (Exception e) {
                html.append("<tr><td colspan='5'>Error loading schedule data.</td></tr>");
            }

            html.append("</table></body></html>");

            // 3. Send the HTML back to the web browser
            String response = html.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}