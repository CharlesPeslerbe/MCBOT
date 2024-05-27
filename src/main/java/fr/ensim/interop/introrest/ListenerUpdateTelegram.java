package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.controller.MessageRestController;
import fr.ensim.interop.introrest.HandlerMessageTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {

	private final MessageRestController messageRestController = new MessageRestController();
	private final HandlerMessageTelegram handlerMessageTelegram = new HandlerMessageTelegram();


	@Override
	public void run(String... args) throws Exception {
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");

		// Operation de pooling pour capter les évènements Telegram
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					List<Update> response = messageRestController.recevoirMaj().getBody().getResult();
					handlerMessageTelegram.handleMessage(response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 1000);
	}
}
