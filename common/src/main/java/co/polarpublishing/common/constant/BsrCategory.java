package co.polarpublishing.common.constant;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum BsrCategory {

  BOOKS("libri,bücher,books,livres,libros"),
  KINDLE("kindle store,kindle-shop,boutique kindle,tienda kindle"),
  AUDIOBOOKS("audible audiobooks,audible hörbücher & originals," +
      "audible audiobooks & originals," +
      "audible books & originals," +
      "livres audio audible & originals," +
      "livres et œuvres originales audible," +
      "audible libros y originales");

  private final List<String> amazonLabels;

  private BsrCategory(String amazonLabelsString) {
    String[] amazonLabels = amazonLabelsString.split(",");
    this.amazonLabels = Arrays.asList(amazonLabels);
  }

  public List<String> getAmazonLabels() {
    return this.amazonLabels;
  }

  public static BsrCategory findByAmazonLabel(String amazonLabel) {
    log.debug("Finding bsr category by amazon label {}.", amazonLabel);
    if (BOOKS.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return BOOKS;
    }
    if (KINDLE.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return KINDLE;
    }
    if (AUDIOBOOKS.amazonLabels.contains(amazonLabel.toLowerCase())) {
      return AUDIOBOOKS;
    }
    log.warn("Can't find allowed book type {}.", amazonLabel);
    return null;
  }

}
