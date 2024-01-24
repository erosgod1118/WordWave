package co.polarpublishing.userservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ChromeExtensionStateDto {

  Integer remainingTokensForBsrHistory;
  Integer remainingTokensForChromeExtensionUsage;
  Integer maxChromeExtensionUsageQuantity;
  
}
