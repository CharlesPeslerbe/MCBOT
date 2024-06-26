package fr.ensim.interop.introrest.controller;

import fr.ensim.interop.introrest.api.mc.MessageRequest;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageRestController {

	private static final Logger log = LoggerFactory.getLogger(MessageRestController.class);

	private static String telegramApiUrl= "https://api.telegram.org/bot";

	private static String botToken="7168016984:AAHamVt6FMdr4wB70UituyJTSuDqms7m4oo";

	private static String chatId="6821361859";

	private static String chatIdStevan="6605178163";

	private RestTemplate restTemplate = new RestTemplate();
	private int lastOffset = 0;




	@PostMapping("/sendMsg")
	public ResponseEntity<ApiResponseTelegram> envoyerMessage(@RequestBody MessageRequest msg){

		// Si le message est vide, on retourne une erreur 400
		if(!StringUtils.hasText(msg.getText())){
			return ResponseEntity.badRequest().build();
		}
		// Si le chat_id est vide, on le remplace par le chat_id par défaut
		if(!StringUtils.hasText(msg.getChat_id())){
			msg.setChat_id(chatId);
		}

		// On envoie le message
		String sendMessageUrl = telegramApiUrl + botToken + "/sendMessage";
		ApiResponseTelegram messageResponse = restTemplate.postForObject(sendMessageUrl, msg, ApiResponseTelegram.class);
		return ResponseEntity.ok().body(messageResponse);
	}

	@GetMapping("/getUpt")
	public ResponseEntity<ApiResponseUpdateTelegram> recevoirMaj(){
		String getUpdatesUrl = telegramApiUrl + botToken + "/getUpdates?offset=" + (lastOffset + 1);
		ApiResponseUpdateTelegram updateResponse = restTemplate.getForObject(getUpdatesUrl, ApiResponseUpdateTelegram.class);

		if (updateResponse != null && !updateResponse.getResult().isEmpty()) {
			lastOffset = updateResponse.getResult().get(updateResponse.getResult().size() - 1).getUpdateId();
		}
		return ResponseEntity.ok().body(updateResponse);
	}
}
