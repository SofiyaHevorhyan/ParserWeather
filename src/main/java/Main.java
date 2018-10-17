/*
 * Created by Victor on 03.10.2018.
 * Simple program for getting weather for some Ukrainian cities
 */

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

import static java.lang.String.format;


public class Main {

    public static String URL = "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0" +
            "%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";

    @SneakyThrows
    public static void main(String[] args) {

        System.out.println("Get the weather for city. If the name won't be full, the first suggested will be provided");
        System.out.println(format("Find examples here: %s", Main.URL));
        System.out.println("Please, enter the name of city: ");
        Scanner sc = new Scanner(System.in);
        String nameCity = sc.next();

        Document doc = Jsoup.connect(Main.URL).get();
        Elements cities = doc.select("table tr");
        City[] parsedCities = new City[cities.size()];

        int counter = 0;
        for (Element city : cities) {

            City myCity = City.parse(city);
            if (myCity != null) {
                parsedCities[counter] = myCity;

                if (myCity.getName().contains(nameCity)) {
                    System.out.println("found");
                    break;
                }
                counter++;
            }
        }
        if (parsedCities[counter] != null) {
            System.out.println("weather");
            System.out.println(parsedCities[counter].getWeather());
        } else {
            System.out.println(format("Sorry, we couldn't find city %s in out list", nameCity));
        }
    }
}