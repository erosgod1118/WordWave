package co.polarpublishing.dbcommon.entity;

public enum Feature {

  CE_BSR_HISTORY("CE_BSR_HISTORY"), CHROME_EXTENSION("CHROME_EXTENSION"), LISTING_ANALYSIS("LISTING_ANALYSIS");

  private String name;

  Feature(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
  
}
