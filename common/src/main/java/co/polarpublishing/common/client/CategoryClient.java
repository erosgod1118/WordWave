package co.polarpublishing.common.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "categories", url = "category-explorer-service")
@RequestMapping("/api/v1/marketplaces/{marketplaceId}/categories")
public interface CategoryClient {

  @GetMapping(path = "/{categoryId}/parents")
  List<CategoryDto> getParentCategories(@PathVariable("marketplaceId") long marketplaceId,
      @PathVariable("categoryId") long categoryId);

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @EqualsAndHashCode
  class CategoryDto {

    private Long id;
    private Long amazonId;
    private String name;
    private Integer level;
    private String amazonUrl;
    private Long parentAmazonId;
    private List<CategoryDto> parents;
    private String pathString;

  }

}
