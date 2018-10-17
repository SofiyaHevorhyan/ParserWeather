/*
 * Class Coordinates for representation of latitude and longitude of the city
 * Created by Sofiya Hevorhyan on 15.10.2018
 */

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


@Getter
@Setter
@ToString
public class Coordinates {
    private String lat;
    private String longitude;

    @SneakyThrows
    public static Coordinates parse(String url) {
        Element geo = Jsoup.connect(url).get().select("span.geo").first();
        if (geo != null) {
            String[] coordinates = geo.text().split("[; ]+");

            Coordinates myCoordinates = new Coordinates();
            myCoordinates.setLat(coordinates[0]);
            myCoordinates.setLongitude(coordinates[1]);

            return myCoordinates;
        }
        return null;
    }
}