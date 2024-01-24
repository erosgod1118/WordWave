package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.common.dto.MailDto;
import co.polarpublishing.userservice.service.MailService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

	private final JavaMailSender javaMailSender;

	@Autowired
	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	@Override
	public void send(MailDto mailDto) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom("support@bookbeam.io");
		mailMessage.setTo(mailDto.getTo());
		mailMessage.setSubject(mailDto.getSubject());
		mailMessage.setText(mailDto.getMessage());

		javaMailSender.send(mailMessage);
	}

	@Override
	public void sendHtml(MailDto mailDto) {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage,"utf-8");

		try {
				helper.setFrom("support@bookbeam.io");
				helper.setTo(mailDto.getTo());
				helper.setSubject(mailDto.getSubject());
				helper.setText(mailDto.getMessage(), true);
		} catch (MessagingException ex) {
				log.error("Error sending mail {0}", ex);
		}

		javaMailSender.send(mailMessage);
	}

	@Override
	public void sendHtml(MailDto mailDto, String from) {
		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage,"utf-8");

		try {
				helper.setFrom(from);
				helper.setTo(mailDto.getTo().split(","));
				helper.setSubject(mailDto.getSubject());
				helper.setText(mailDto.getMessage(), true);
		} catch (MessagingException ex) {
				log.error("Error sending mail {0}", ex);
		}
		
		javaMailSender.send(mailMessage);
	}

}

