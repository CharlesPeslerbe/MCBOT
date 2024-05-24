package fr.ensim.interop.introrest.client;

import fr.ensim.interop.introrest.controller.MessageRestController;

public class ClientRestTest {
	
	public static void main(String[] args) {

		String msg = "Attention votre compte à été piraté !";
		//Vous pouvez faire des tests d'appels d'API ici


		//Exemple d'appel à l'API Telegram


		MessageRestController messageRestController = new MessageRestController();
		//messageRestController.sendMessage(msg);
	}
}
