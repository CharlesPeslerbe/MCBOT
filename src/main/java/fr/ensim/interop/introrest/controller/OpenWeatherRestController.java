package fr.ensim.interop.introrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class OpenWeatherRestController {

        //Vous pouvez ajouter des méthodes ici pour appeler l'API OpenWeather
        //https://openweathermap.org/api

    private static final String API_KEY = "" ;

    @GetMapping(value = "/weather",params = {"lat","lon"})
    public ResponseEntity<>
}
