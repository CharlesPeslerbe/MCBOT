package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.api.mc.MessageRequest;
import fr.ensim.interop.introrest.api.mc.OpenWeatherForecast;
import fr.ensim.interop.introrest.controller.OpenWeatherRestController;
import fr.ensim.interop.introrest.model.telegram.Message;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import fr.ensim.interop.introrest.controller.MessageRestController;

import java.util.List;

public class HandlerMessageTelegram {
    private final MessageRestController messageRestController = new MessageRestController();
    private final OpenWeatherRestController openWeatherRestController = new OpenWeatherRestController();


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
            forecastMessage = "Désolé, je n'ai pas pu trouver les prévisions météorologiques pour " + cityName + " :("; //mettre la valeur par défaut plutôt qu'else
        }

        MessageRequest messageRequest = new MessageRequest(forecastMessage);
        messageRestController.envoyerMessage(messageRequest);
    }
}

