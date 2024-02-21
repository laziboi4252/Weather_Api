import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

import org.json.JSONObject; //Download the org.json package from google

public class Weather_API {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter City/State/Country :  ");
		String city = sc.next();
        String apiKey = "";//get apiKey from http://openweathermap.org
        getweatherReport(city, apiKey);
        sc.close();

        
	}
	public static void getweatherReport(String city, String apiKey)  {
		try {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            URI uri = new URI(url);

			HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                
                JSONObject jsonResponse = new JSONObject(response.toString());

                
                if (jsonResponse.has("weather")) {
                    JSONObject weatherObject = jsonResponse.getJSONArray("weather").getJSONObject(0);
                    String description = weatherObject.getString("description");

                    
                    if (jsonResponse.has("main")) {
                        JSONObject mainObject = jsonResponse.getJSONObject("main");
                        double temperature = mainObject.getDouble("temp");

                        
                        System.out.println("Weather: " + description + "\nTemperature: " + temperature + " °C");
                    }
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        	System.out.println("Error fetching or processing data.");
        }
	}

}
