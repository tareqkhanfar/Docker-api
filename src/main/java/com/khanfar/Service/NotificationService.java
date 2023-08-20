package com.khanfar.Service;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@ApplicationScoped
public class NotificationService {

    public void sendNotification(String apiUrl) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                // Handle the response as needed (e.g., logging, further processing)
                System.out.println("API Response: " + responseBody);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., connection error, timeout)
            e.printStackTrace();
        }
    }
}
