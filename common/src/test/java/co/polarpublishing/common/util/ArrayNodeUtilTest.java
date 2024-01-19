package co.polarpublishing.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ArrayNodeUtilTest {

	private ObjectMapper objectMapper = new ObjectMapper();
	private ArrayNode dummyArrayNode;

	@Before
	public void setUp() {
		this.dummyArrayNode = this.objectMapper.createArrayNode();
		this.dummyArrayNode.add(1);
		this.dummyArrayNode.add(2);
		this.dummyArrayNode.add(3);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSubArrayNode_WithNullSource_ShouldGetNPE() {
		ArrayNodeUtil.getSubArrayNode(null, 1, 2);
	}

	@Test
	public void testGetSubArrayNode_ShouldGetArrayNodeIncludingUpperIndex() {
		ArrayNode arrayNode = ArrayNodeUtil.getSubArrayNode(this.dummyArrayNode, 1, 2);

		Assert.assertEquals(2, arrayNode.get(0).asInt());
	}

	@Test
	public void testGetSubArrayNode_ShouldGetArrayNodeExcludingLowerIndex() {
		ArrayNode arrayNode = ArrayNodeUtil.getSubArrayNode(this.dummyArrayNode,
				1, 2);

		Assert.assertEquals(1, arrayNode.size());
		Assert.assertEquals(2, arrayNode.get(0).asInt());
	}

}
