package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.api.mc.Joke;
import fr.ensim.interop.introrest.api.mc.JokeResponse;
import fr.ensim.interop.introrest.api.mc.MessageRequest;
import fr.ensim.interop.introrest.api.mc.OpenWeatherForecast;
import fr.ensim.interop.introrest.controller.OpenWeatherRestController;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import fr.ensim.interop.introrest.controller.MessageRestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

public class HandlerMessageTelegram {
    private final MessageRestController messageRestController = new MessageRestController();
    private final OpenWeatherRestController openWeatherRestController = new OpenWeatherRestController();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String jokeApiUrl = "https://blague-api.vercel.app/api?mode=global";

    public void handleMessage(List <Update> response) {
        if (response != null){
            for (Update update : response) {
                Message message = update.getMessage();
                if (message != null) {
                    String text = message.getText().toLowerCase();
                    if (text.contains("météo")) {
                        String cityName = "Le Mans";
                        sendWeatherForecast(cityName);
                    }
                    else if (text.contains("blague")){
                        sendJoke();
                    }
                }
            }
        }
    }

    private String ForecastMessage(OpenWeatherForecast forecast) {
        StringBuilder message = new StringBuilder("Prévisions météo pour les prochaines heures :\n");
        forecast.getList().forEach(f -> {
            message.append(f.getDt_txt()).append(": ")
                    .append(f.getMain().getTemp()).append("°C, ")
                    .append(f.getWeather().get(0).getDescription()).append("\n");
        });
        return message.toString();
    }

    private void sendWeatherForecast(String cityName) {
        ResponseEntity<OpenWeatherForecast> forecastResponse = openWeatherRestController.meteoPrevision(cityName);
        String forecastMessage;

        if (forecastResponse.getStatusCode() == HttpStatus.OK && forecastResponse.getBody() != null) {
            forecastMessage = ForecastMessage(forecastResponse.getBody());
        } else {
            forecastMessage = "Aucunes prévisions météorologiques trouvées pour " + cityName + " :("; //mettre la valeur par défaut plutôt qu'else
        }

        MessageRequest messageRequest = new MessageRequest(forecastMessage);
        messageRestController.envoyerMessage(messageRequest);
    }

    private void sendJoke(){
        ResponseEntity<JokeResponse> jokeResponse = restTemplate.getForEntity(jokeApiUrl, JokeResponse.class);
        if (jokeResponse.getStatusCode() == HttpStatus.OK && jokeResponse.getBody() != null) {
            JokeResponse jokeApi = jokeResponse.getBody();
            Joke joke = new Joke(new Random().nextInt(1000), "Blague du jour", jokeApi.getBlague() + " " + jokeApi.getReponse(), new Random().nextInt(11));
            String jokeMessage = joke.getTitle() + "\n" + joke.getText() + "\nNote : " + joke.getRating() + "/10";
            MessageRequest messageRequest = new MessageRequest(jokeMessage);
            messageRestController.envoyerMessage(messageRequest);
        } else {
            MessageRequest messageRequest = new MessageRequest("Désolé, pas de blagounette aujourd'hui :(");
            messageRestController.envoyerMessage(messageRequest);
        }
    }
}

