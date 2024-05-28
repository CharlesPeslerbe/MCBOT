package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.api.mc.Joke;
import fr.ensim.interop.introrest.api.mc.JokeResponse;
import fr.ensim.interop.introrest.api.mc.MessageRequest;
import fr.ensim.interop.introrest.controller.MessageRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class JokeRestController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/joke")
    public ResponseEntity<JokeResponse> recevoirBlague(@RequestParam("mode") String jokeMode){
        String jokeApiUrl = "https://blague-api.vercel.app/api?mode=" + jokeMode;
        ResponseEntity<JokeResponse> responseEntity = restTemplate.getForEntity(jokeApiUrl, JokeResponse.class);
        JokeResponse jokeResponse = responseEntity.getBody();
        if (jokeResponse != null) {
            return responseEntity;
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
