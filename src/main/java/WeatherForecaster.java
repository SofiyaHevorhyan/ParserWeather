/*
 * Simple class for getting the weather for the city (class City)
 */


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

@Setter
@ToString
public class WeatherForecaster {
    String country = "";
    String name = "";
    String time = "";
    double temp_c;
    double pressure;
    String text = "";


    public static String ADDRESS = "http://api.apixu.com/v1/current.json?key=" +
            "9177376b777249cc88d154643181710&q=";

    public static String forecast(City city) throws UnirestException {

        if (city.getCoordinates() == null) {
            return "Sorry, we don't support city " + city.getName();
        }

        StringBuilder request = new StringBuilder();
        request.append(WeatherForecaster.ADDRESS);
        request.append(city.getCoordinates().getLat());
        request.append("%20");
        request.append(city.getCoordinates().getLongitude());

        HttpResponse<JsonNode> weather = Unirest.post(request.toString()).asJson();
        return WeatherForecaster.response(weather.getBody().getObject());
    }
    private static String response(JSONObject weather) throws JSONException {
        JSONObject name = weather.getJSONObject("location");
        JSONObject data = weather.getJSONObject("current");

        WeatherForecaster today = new WeatherForecaster();
        today.setCountry(name.getString("country"));
        today.setName(name.getString("name"));
        today.setTime(name.getString("localtime"));

        today.setTemp_c(data.getDouble("feelslike_c"));
        today.setPressure(data.getDouble("pressure_in"));
        today.setText(data.getJSONObject("condition").getString("text"));

        return today.toString().replace("=", ": ");
    }
}