package co.polarpublishing.common.model;

import org.junit.Assert;
import org.junit.Test;

public class URLTest {

  @Test(expected = IllegalArgumentException.class)
  public void testURL_WithNullArg() {
    new URL(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testURL_WithEmptyArg() {
    new URL("   ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testURL_WithShortInvalidUrl() {
    new URL(".c");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testURL_WithLongInvalidUrl() {
    new URL("hello world");
  }

  @Test
  public void testURL1() {
    URL url = new URL("amazon.com");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
  }

  @Test
  public void testURL2() {
    URL url = new URL("https://amazon.com");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("https", url.getProtocol());
  }

  @Test
  public void testURL3() {
    URL url = new URL("http://amazon.com");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("http", url.getProtocol());
  }

  @Test
  public void testURL4() {
    URL url = new URL("http://amazon.com/dp/qerew23");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("http", url.getProtocol());
    Assert.assertEquals("/dp/qerew23", url.getPath());
  }

  @Test
  public void testURL5() {
    URL url = new URL("http://amazon.com/dp/qerew23?ref=1232");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("http", url.getProtocol());
    Assert.assertEquals("/dp/qerew23", url.getPath());
    Assert.assertNotNull(url.getQueryParams());
    Assert.assertEquals(1, url.getQueryParams().size());
  }

  @Test
  public void testURL6() {
    URL url = new URL("http://amazon.com/dp/qerew23?ref=1232&test=123");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("http", url.getProtocol());
    Assert.assertEquals("/dp/qerew23", url.getPath());
    Assert.assertNotNull(url.getQueryParams());
    Assert.assertEquals(2, url.getQueryParams().size());
  }

  @Test
  public void testURL7() {
    URL url = new URL("http://blog.amazon.com/dp/qerew23?ref=1232&test=123");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("com", url.getPublicSuffix());
    Assert.assertEquals("blog", url.getSubDomainName());
    Assert.assertEquals("http", url.getProtocol());
    Assert.assertEquals("/dp/qerew23", url.getPath());
    Assert.assertNotNull(url.getQueryParams());
    Assert.assertEquals(2, url.getQueryParams().size());
  }

  @Test
  public void testURL8() {
    URL url = new URL("http://www.amazon.co.uk/dp/qerew23?ref=1232&test=123");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("co.uk", url.getPublicSuffix());
    Assert.assertEquals("www", url.getSubDomainName());
    Assert.assertEquals("http", url.getProtocol());
    Assert.assertEquals("/dp/qerew23", url.getPath());
    Assert.assertNotNull(url.getQueryParams());
    Assert.assertEquals(2, url.getQueryParams().size());
  }

  @Test
  public void testURL9() {
    URL url = new URL("amazon.co.uk");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("co.uk", url.getPublicSuffix());
  }

  @Test
  public void testURL10() {
    URL url = new URL("amazon.it");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("it", url.getPublicSuffix());
  }

  @Test
  public void testURL11() {
    URL url = new URL("amazon.de");

    Assert.assertNotNull(url);
    Assert.assertEquals("amazon", url.getDomainName());
    Assert.assertEquals("de", url.getPublicSuffix());
  }

}
