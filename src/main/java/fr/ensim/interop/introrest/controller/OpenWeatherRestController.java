package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.api.mc.GeoResponse;
import fr.ensim.interop.introrest.api.mc.OpenWeather;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OpenWeatherRestController {

    private static final String OPENWEATHER_API_KEY = "7ff12034fc6cc465f1d3529c6873523f";
    private static final String GEO_API_URL = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    @GetMapping("/weather")
    public ResponseEntity<OpenWeather> getWeatherByCityName(@RequestParam("city") String cityName) {
        // Appel API pour récupérer les coordonnées de la ville à partir de son nom
        String geoApiUrl = GEO_API_URL + "?q=" + cityName + "&limit=1&appid=" + OPENWEATHER_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GeoResponse[]> geoResponse = restTemplate.getForEntity(geoApiUrl, GeoResponse[].class);

        // Gestion des erreurs
        if (geoResponse.getStatusCode() != HttpStatus.OK || geoResponse.getBody() == null || geoResponse.getBody().length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Récupération des coordonnées de la ville à partir de la réponse de l'API
        String lat = geoResponse.getBody()[0].getLat();
        String lon = geoResponse.getBody()[0].getLon();

        // Appel API pour récupérer les informations météo à partir des coordonnées de la ville
        String weatherApiUrl = WEATHER_API_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + OPENWEATHER_API_KEY;
        ResponseEntity<OpenWeather> weatherResponse = restTemplate.getForEntity(weatherApiUrl, OpenWeather.class);

        // Gestion des erreurs
        if (weatherResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(weatherResponse.getStatusCode()).body(null);
        }

        return ResponseEntity.ok().body(weatherResponse.getBody());
    }

}

