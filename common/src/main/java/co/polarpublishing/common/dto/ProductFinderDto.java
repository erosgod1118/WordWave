package co.polarpublishing.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFinderDto {

  private List<BookDetailsResponse> booksDetails;

  private Integer totalResults;

}
