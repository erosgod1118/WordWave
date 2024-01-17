package co.polarpublishing.dbcommon.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "subscription_plans")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SubscriptionPlan extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "id.subscriptionPlan")
  private List<FeatureUsageLimit> featureUsageLimits;

  public FeatureUsageLimit getFeatureUsageLimit(Feature feature) {
    if (this.featureUsageLimits == null || this.featureUsageLimits.size() == 0) {
      return null;
    }

    return this.featureUsageLimits.stream()
        .filter(limit -> limit.getId().getFeature() == feature)
        .findFirst()
        .get();
  }
}
