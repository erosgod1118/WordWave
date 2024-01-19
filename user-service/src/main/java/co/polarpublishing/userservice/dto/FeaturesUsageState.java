package co.polarpublishing.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeaturesUsageState {

  private UsageState listingAnalysisFeatureUsageState = new UsageState();
}
