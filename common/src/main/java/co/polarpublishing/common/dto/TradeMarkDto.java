package co.polarpublishing.common.dto;

import lombok.*;

/**
 * TradeMark data transfer object.
 *
 * @author FMRGJ
 */
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TradeMarkDto {

	private String keyword;
	private String registration_number;
	private String serial_number;
	private String status_label;
	private String status_code;
	private String status_date;
	private String status_definition;
	private String filing_date;
	private String registration_date;
	private String abandonment_date;
	private String expiration_date;
	private String description;
	private Object[] owners;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRegistration_number() {
		return registration_number;
	}

	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_date() {
		return status_date;
	}

	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}

	public String getStatus_definition() {
		return status_definition;
	}

	public void setStatus_definition(String status_definition) {
		this.status_definition = status_definition;
	}

	public String getFiling_date() {
		return filing_date;
	}

	public void setFiling_date(String filing_date) {
		this.filing_date = filing_date;
	}

	public String getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}

	public String getAbandonment_date() {
		return abandonment_date;
	}

	public void setAbandonment_date(String abandonment_date) {
		this.abandonment_date = abandonment_date;
	}

	public String getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
