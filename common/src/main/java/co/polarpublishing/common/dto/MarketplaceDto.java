package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Marketplace data transfer object.
 *
 * @author FMRGJ
 */
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MarketplaceDto {

	private Long id;

	private String name;
	private String amazonId;
	private String countryName;
	private String countryCode;

	// https://developers.google.com/adwords/api/docs/appendix/codes-formats
	private Long googleLocationId;
	// https://developers.google.com/google-ads/api/reference/data/geotargets
	private Long googleLanguageId;

	public String getAmazonId() {
		return amazonId;
	}

	public void setAmazonId(String amazonId) {
		this.amazonId = amazonId.trim();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName.trim();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode.trim();
	}

	public Long getGoogleLocationId() {
		return googleLocationId;
	}

	public void setGoogleLocationId(Long googleLocationId) {
		this.googleLocationId = googleLocationId;
	}

	public Long getGoogleLanguageId() {
		return googleLanguageId;
	}

	public void setGoogleLanguageId(Long googleLanguageId) {
		this.googleLanguageId = googleLanguageId;
	}

}
