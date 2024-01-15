package co.polarpublishing.common.dto;

import co.polarpublishing.common.model.StoreTypeName;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Store data transfer object.
 *
 * @author FMRGJ
 */
@Builder
@Getter
@Setter
public class StoreDto {

  private Long id;
  private String name;
  private String amazonSearchAlias;
  private String amazonId;
  private StoreTypeName storeTypeName;
  private Long rootCategoryId;

  @Override
  public String toString() {
    return "StoreDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", amazonSearchAlias='" + amazonSearchAlias + '\'' +
        ", amazonId='" + amazonId + '\'' +
        ", storeTypeName=" + storeTypeName +
        ", rootCategoryId=" + rootCategoryId +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StoreDto storeDto = (StoreDto) o;
    return Objects.equals(id, storeDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
