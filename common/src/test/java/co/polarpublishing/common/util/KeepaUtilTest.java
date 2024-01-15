package co.polarpublishing.common.util;

import org.junit.Assert;
import org.junit.Test;

public class KeepaUtilTest {

	@Test(expected = NullPointerException.class)
	public void testConvertKeepaPriceToWordwavePrice_WithNull_ShouldGetNullPointerException() {
		KeepaUtil.convertKeepaPriceToWordwavePrice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvertKeepaPriceToWordwavePrice_WithNonDigit_ShouldGetIllegalArgumentException() {
		KeepaUtil.convertKeepaPriceToWordwavePrice("abc");
	}

	@Test
	public void testConvertKeepaPriceToWordwavePrice_WithThreeDigit_ShouldWork() {
		double price = KeepaUtil.convertKeepaPriceToWordwavePrice("123");
		Assert.assertEquals(price, 1.23, 0);
	}

	@Test
	public void testConvertKeepaPriceToWordwavePrice_WithTwoDigit_ShouldWork() {
		double price = KeepaUtil.convertKeepaPriceToWordwavePrice("12");
		Assert.assertEquals(price, 0.12, 0);
	}

	@Test
	public void testConvertKeepaPriceToWordwavePrice_WithOneDigit_ShouldWork() {
		double price = KeepaUtil.convertKeepaPriceToWordwavePrice("1");

		Assert.assertEquals(price, 0.01, 0);
	}

}
