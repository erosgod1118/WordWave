package co.polarpublishing.userservice.service;

import co.polarpublishing.common.dto.MailDto;

/** The {@code MailService} interface provides methods in order to send email. */
public interface MailService {

  /**
   * Sends message to the specified email address.
   *
   * @param mailDto {@link MailDto} object.
   */
  void send(MailDto mailDto);

  /**
   * Sends message to the specified email address.
   *
   * @param mailDto {@link MailDto} object.
   */
  void sendHtml(MailDto mailDto);

  /**
   * Sends message to the specified email address from a specific address.
   *
   * @param mailDto {@link MailDto} object.
   */
  void sendHtml(MailDto mailDto, String from);
  
}
