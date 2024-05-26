package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.api.mc.OpenWeather;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherRestController {

        //Vous pouvez ajouter des méthodes ici pour appeler l'API OpenWeather
        //https://openweathermap.org/api

    private static final String API_KEY = "" ;

    @GetMapping(value = "/weather",params = {"lat","lon"})
    public ResponseEntity<OpenWeather> meteo (
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon ){

        RestTemplate restTemplate = new RestTemplate();
        OpenWeather openWeather = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+API_KEY, OpenWeather.class, lat, lon);

        return ResponseEntity.ok().body(openWeather);
    }
    
}
