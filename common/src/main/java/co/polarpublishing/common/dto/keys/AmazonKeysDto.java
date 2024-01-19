package co.polarpublishing.common.dto.keys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmazonKeysDto {

	@ApiModelProperty(hidden = true)
	private long id;

	@NotNull(message = "Missing required parameter 'associate id'.")
	private String associateId;

	@NotNull(message = "Missing required parameter 'access key'.")
	private String accessKey;

	@NotNull(message = "Missing required parameter 'secret key'.")
	private String secretKey;

	@NotNull(message = "Missing required parameter 'marketplace'.")
	private MarketplaceKeysDto marketplace;

}
