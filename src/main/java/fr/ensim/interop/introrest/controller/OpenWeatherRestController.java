package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.api.mc.GeoResponse;
import fr.ensim.interop.introrest.api.mc.OpenWeather;
import fr.ensim.interop.introrest.api.mc.OpenWeatherForecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OpenWeatherRestController {

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherRestController.class);

    private static final String OPENWEATHER_API_KEY = "7ff12034fc6cc465f1d3529c6873523f";
    private static final String GEO_API_URL = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast";

    @GetMapping("/weather")
    public ResponseEntity<OpenWeather> meteo(@RequestParam("city") String cityName) {

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

        // Appel API pour récupérer les informations météo à partir des coordonnées de la ville (°C
        String weatherApiUrl = WEATHER_API_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + OPENWEATHER_API_KEY + "&units=metric";
        ResponseEntity<OpenWeather> weatherResponse = restTemplate.getForEntity(weatherApiUrl, OpenWeather.class);

        // Gestion des erreurs
        if (weatherResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(weatherResponse.getStatusCode()).body(null);
        }
        return ResponseEntity.ok().body(weatherResponse.getBody());
    }

    @GetMapping("/forecast")
    public ResponseEntity<OpenWeatherForecast> meteoPrevision(@RequestParam("city") String cityName, @RequestParam("timestamp") Integer timestamp) {

        RestTemplate restTemplate = new RestTemplate();
        String geoApiUrl = GEO_API_URL + "?q=" + cityName + "&limit=1&appid=" + OPENWEATHER_API_KEY;

        ResponseEntity<GeoResponse[]> geoResponse = restTemplate.getForEntity(geoApiUrl, GeoResponse[].class);

        // Gestion des erreurs
        if (geoResponse.getStatusCode() != HttpStatus.OK || geoResponse.getBody() == null || geoResponse.getBody().length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Récupération des coordonnées de la ville à partir de la réponse de l'API
        String lat = geoResponse.getBody()[0].getLat();
        String lon = geoResponse.getBody()[0].getLon();
        // Un jour = 8 timestamp
        timestamp *= 8;


        // Appel API pour récupérer les prévisions météorologiques à partir des coordonnées de la ville
        String forecastApiUrl = FORECAST_API_URL + "?lat=" + lat + "&lon=" + lon + "&cnt=" + timestamp + "&appid=" + OPENWEATHER_API_KEY + "&units=metric";

        ResponseEntity<OpenWeatherForecast> forecastResponse = restTemplate.getForEntity(forecastApiUrl, OpenWeatherForecast.class);

        // Gestion des erreurs
        if (forecastResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(forecastResponse.getStatusCode()).body(null);
        }

        return ResponseEntity.ok().body(forecastResponse.getBody());
    }
}

