package co.polarpublishing.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArrayNodeUtil {

	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static ArrayNode getSubArrayNode(ArrayNode source, int fromIndex, int toIndex) {
		log.trace("Generating sub array from {} to {}.", fromIndex, toIndex);

		ArrayNode arrayNode = OBJECT_MAPPER.createArrayNode();

		while (fromIndex < toIndex) {
			arrayNode.add(source.get(fromIndex));
			fromIndex++;
		}

		return arrayNode;
	}

}
