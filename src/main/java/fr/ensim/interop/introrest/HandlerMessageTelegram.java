package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.api.mc.Joke;
import fr.ensim.interop.introrest.api.mc.JokeResponse;
import fr.ensim.interop.introrest.api.mc.MessageRequest;
import fr.ensim.interop.introrest.api.mc.OpenWeatherForecast;
import fr.ensim.interop.introrest.controller.JokeRestController;
import fr.ensim.interop.introrest.controller.OpenWeatherRestController;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import fr.ensim.interop.introrest.controller.MessageRestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HandlerMessageTelegram {
    private final MessageRestController messageRestController = new MessageRestController();
    private final OpenWeatherRestController openWeatherRestController = new OpenWeatherRestController();
    private final RestTemplate restTemplate = new RestTemplate();
    private final JokeRestController jokeRestController = new JokeRestController();
    private final String jokeApiUrl = "https://blague-api.vercel.app/api?mode=global";
    private Integer daysCount = 1;
    private String jokeMode = "global";

    public void handleMessage(List <Update> response) {
        if (response != null){
            for (Update update : response) {
                Message message = update.getMessage();
                if (message != null) {
                    String text = message.getText().toLowerCase();
                    // Test du cas météo
                    if (text.contains("météo")) {
                        // On sépare la ville du nombre de jours
                        String[] param = text.substring(6).split("/");
                        // Dans la liste param ( [0] = ville, [1] = nombre de jours)
                        String cityName = param[0]; //ville
                        // Si le nombre de jours est renseigné, on prends la nouvelle valeur (par défaut nombre de jours = 1)
                        if (param.length > 1){
                            daysCount = Integer.parseInt(param[1]);
                        }
                        sendWeatherForecast(cityName, daysCount);
                    }
                    else if (text.contains("blague")){
                        String[] param = text.split(" ");
                        if (param.length > 1){
                            jokeMode = param[1];
                            // Gestion de l'erreur si un mode inconnu
                            String[] jokesModes = {"dark", "global", "dev"};
                            for(String j : jokesModes){
                                if(jokeMode.equals(j)){
                                    jokeMode = j;
                                }
                            }
                        }
                        sendJoke(jokeMode);
                    }
                    else {
                        MessageRequest messageRequest = new MessageRequest("Je ne comprends pas votre demande :( \n" +
                                "En tant que Chatbot je ne suis disposé qu'à répondre aux demandes suivantes : \n" +
                                "- Météo [Ville]/[NbJours]: pour obtenir la météo d'une ville, (NbJours est optionnel)\n" +
                                "- Blague [Type]: pour obtenir une blague aléatoire, parmis les types suivants : global, dev, dark (Type est optionnel) \n");
                        messageRestController.envoyerMessage(messageRequest);
                    }
                }
            }
        }
    }

    private String ForecastMessage(OpenWeatherForecast forecast) {
        // Construire une string
        StringBuilder message = new StringBuilder("Prévisions météo pour les prochaines heures :\n");
        forecast.getList().forEach(f -> {
            String format = "%s h -> %s°C, %s\n";
            //Convertir et arrondir (à l'entier le plus proche) la température
            String temp = String.valueOf(Math.round(f.getMain().getTemp()));
            Date dt = new Date(f.getDt() * 1000L);
            String description = f.getWeather().get(0).getDescription();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH", Locale.FRENCH);
            String dtTxt = dateFormat.format(dt);
            String formattedMessage = String.format(format, dtTxt, temp, description);
            message.append(formattedMessage);
        });
        return message.toString();
    }

    private void sendWeatherForecast(String cityName, Integer daysCount) {
        ResponseEntity<OpenWeatherForecast> forecastResponse = openWeatherRestController.meteoPrevision(cityName, daysCount);
        String forecastMessage;

        if (forecastResponse.getStatusCode() == HttpStatus.OK && forecastResponse.getBody() != null) {
            forecastMessage = ForecastMessage(forecastResponse.getBody());
        } else {
            forecastMessage = "Aucunes prévisions météorologiques trouvées pour " + cityName + " :(";
        }

        MessageRequest messageRequest = new MessageRequest(forecastMessage);
        messageRestController.envoyerMessage(messageRequest);
    }

    private void sendJoke(String jokeMode) {
        ResponseEntity<JokeResponse> jokeResponse = jokeRestController.recevoirBlague(jokeMode);
        if (jokeResponse.getStatusCode() == HttpStatus.OK && jokeResponse.getBody() != null) {
            JokeResponse jokeApi = jokeResponse.getBody();
            Joke joke = new Joke(new Random().nextInt(1000), "Blague du jour en mode "+jokeMode, jokeApi.getBlague() + " " + jokeApi.getReponse(), new Random().nextInt(11));
            String jokeMessage = joke.getTitle() + "\n" + joke.getText() + "\nNote : " + joke.getRating() + "/10";
            MessageRequest messageRequest = new MessageRequest(jokeMessage);
            messageRestController.envoyerMessage(messageRequest);
        } else {
            MessageRequest messageRequest = new MessageRequest("Désolé, pas de blagounette aujourd'hui :(");
            messageRestController.envoyerMessage(messageRequest);
        }
    }
}

