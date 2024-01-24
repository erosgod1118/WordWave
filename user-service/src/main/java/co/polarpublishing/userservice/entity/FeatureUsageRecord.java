package co.polarpublishing.userservice.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "feature_usage_records")
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureUsageRecord {

  @EmbeddedId
  private Id id;

  public User getUser() {
    return this.id.getUser();
  }

  public Feature getFeature() {
    return this.id.getFeature();
  }

  public long getTimestamp() {
    return this.id.getTimestamp();
  }

  @Embeddable
  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Id implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Feature feature;

    private long timestamp;

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      
      Id id = (Id) o;
      return timestamp == id.timestamp &&
          user.getId().equals(id.user.getId()) &&
          feature == id.feature;
    }

    @Override
    public int hashCode() {
      return Objects.hash(user.getId(), feature, timestamp);
    }

    @Override
    public String toString() {
      return "Id{" +
          "user=" + user.getId() +
          ", feature=" + feature.name() +
          ", timestamp=" + timestamp +
          '}';
    }
  }

}
