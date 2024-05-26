package fr.ensim.interop.introrest.client;

import fr.ensim.interop.introrest.api.mc.OpenWeather;
import fr.ensim.interop.introrest.controller.MessageRestController;
import fr.ensim.interop.introrest.controller.OpenWeatherRestController;

public class ClientRestTest {
	
	public static void main(String[] args) {

		String msg = "Attention votre compte à été piraté !";
		//Vous pouvez faire des tests d'appels d'API ici


		//Exemple d'appel à l'API Telegram


		//MessageRestController messageRestController = new MessageRestController();
		//messageRestController.sendMessage(msg);

		//Exemple d'appel à l'API Météo

		OpenWeatherRestController openWeatherRestController = new OpenWeatherRestController();
		OpenWeather weather = openWeatherRestController.getWeatherByCityName("Paris").getBody();

	}
}
