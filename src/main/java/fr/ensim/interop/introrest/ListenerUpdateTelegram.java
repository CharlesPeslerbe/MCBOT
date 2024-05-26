package fr.ensim.interop.introrest;

import fr.ensim.interop.introrest.controller.MessageRestController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ListenerUpdateTelegram implements CommandLineRunner {

	private final MessageRestController messageRestController;

	public ListenerUpdateTelegram(MessageRestController messageRestController) {
		this.messageRestController = messageRestController;
	}

	@Override
	public void run(String... args) throws Exception {
		Logger.getLogger("ListenerUpdateTelegram").log(Level.INFO, "Démarage du listener d'updates Telegram...");
		
		// Operation de pooling pour capter les evenements Telegram
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					messageRestController.recevoirMaj();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 10000);
	}
}
