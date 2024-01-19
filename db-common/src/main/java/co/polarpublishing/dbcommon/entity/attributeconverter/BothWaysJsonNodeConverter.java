package co.polarpublishing.dbcommon.entity.attributeconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.type.SerializationException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * An {@link AttributeConverter} that converts json and {@link JsonNode} back
 * and forth.
 *
 * @author FMRGJ
 */
@Slf4j
@Converter
public class BothWaysJsonNodeConverter implements AttributeConverter<JsonNode, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public JsonNode convertToEntityAttribute(String x) {
		if (x == null || x.isEmpty()) {
			return null;
		}

		try {
			return this.objectMapper.readTree(x);
		} catch (IOException ex) {
			log.error(ex.getMessage());
			throw new SerializationException(ex.getMessage(), ex);
		}
	}

	@Override
	public String convertToDatabaseColumn(JsonNode y) {
		String jsonString = null;

		try {
			jsonString = y == null ? null : this.objectMapper.writeValueAsString(y);
		} catch (JsonProcessingException ex) {
			log.error(ex.getMessage());
			throw new RuntimeException(ex.getCause());
		}

		return jsonString;
	}
	
}
