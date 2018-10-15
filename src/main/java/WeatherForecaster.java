import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static java.lang.String.format;

/**
 * Created by Victor on 04.10.2018.
 */
public class WeatherForecaster {

    public static String forecast(City city) throws UnirestException {
        System.out.println(city);
        HttpResponse weather = Unirest.get(format("http://api.apixu.com/v1/current.json?key=" +
                "f6da3a783d34446f8f4120423180410&q=", city.getCoordinates().getLat(), " ",
                city.getCoordinates().getLongitude())).asJson();
        System.out.println(weather);
        return null;

    }

}