/*
 * Created by Victor on 03.10.2018.
 * Simple class for saving info about the city
 */

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Getter
@Setter
@ToString
public class City {
    private String name;
    private String url;
    private String administrativeArea;
    private String numberOfCitizens;
    private String yearOfFound;
    private double area;
    private Coordinates coordinates;

    private static final int INFO_SIZE = 6;


    public static City parse(Element city) {
        Elements info = city.select("td");
        if (info.size() == INFO_SIZE) {
            City myCity = new City();


            Element anchor = info.get(1).select("a").first();
            myCity.setName(anchor.attr("title"));
            myCity.setUrl(String.format("https://uk.wikipedia.org%s", anchor.attr("href")));

            myCity.setAdministrativeArea(info.get(2).select("a").first().attr("title"));
            myCity.setNumberOfCitizens(info.get(3).text());
            myCity.setYearOfFound(info.get(4).select("a").get(0).attr("title"));
            myCity.setArea(Double.parseDouble(info.get(5).text()));
            myCity.setCoordinates(Coordinates.parse(myCity.getUrl()));
            return myCity;
        }
        return null;
    }

    public String getWeather() throws UnirestException {
        return WeatherForecaster.forecast(this);
    }
}
