package co.polarpublishing.common.util;

import org.junit.Assert;
import org.junit.Test;

public class MarketplaceUtilTest {

	// @Test(expected = IllegalArgumentException.class)
	// public void testFormalizeMarketplaceName_WithNullArg() {
	// String formalizedMarketplaceName =
	// MarketplaceUtil.formalizeMarketplaceName(null);
	// }

	// @Test(expected = IllegalArgumentException.class)
	// public void testFormalizeMarketplaceName_WithEmptyArg() {
	// String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName("
	// ");
	// }

	@Test
	public void testFormalizeMarketplaceName_WithTraditionalName() {
		String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName(".com");

		Assert.assertNotNull(formalizedMarketplaceName);
		Assert.assertEquals(".com", formalizedMarketplaceName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFormalizeMarketplaceName_WithUnknownMarketplaceName() {
		String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName(".jpt");

		Assert.assertNotNull(formalizedMarketplaceName);
		Assert.assertEquals(".jpt", formalizedMarketplaceName);
	}

	@Test
	public void testFormalizeMarketplaceName_WithAmazonMarketplace() {
		String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName("amazon.com");

		Assert.assertNotNull(formalizedMarketplaceName);
		Assert.assertEquals(".com", formalizedMarketplaceName);
	}

	@Test
	public void testFormalizeMarketplaceName_WithAudibleMarketplace() {
		String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName("audible.com");

		Assert.assertNotNull(formalizedMarketplaceName);
		Assert.assertEquals("audible.com", formalizedMarketplaceName);
	}

	@Test
	public void testFormalizeMarketplaceName_WithStandardDomain() {
		String formalizedMarketplaceName = MarketplaceUtil.formalizeMarketplaceName(
				"http://www.amazon.com");

		Assert.assertNotNull(formalizedMarketplaceName);
		Assert.assertEquals(".com", formalizedMarketplaceName);
	}

}
