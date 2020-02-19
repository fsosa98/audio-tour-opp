package hr.fer.opp.projekt.audioVodic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.audioVodic.model.User;

@Service
public class NotificationService {

	private JavaMailSender javaMailSender;

	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(User user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("najboljimuzej@gmail.com");
		mail.setSubject("Dobrodošlica");
		mail.setText(
				"Čestitamo na uspješnoj registraciji. Potvrdite Registraciju: https://audiovodic1.herokuapp.com/confirmRegistration/"
						+ user.getId());

		javaMailSender.send(mail);
	}

}
