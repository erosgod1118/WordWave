package co.polarpublishing.dbcommon.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;

/**
 * An {@link AttributeConverter} that converts json and {@link JsonNode} back
 * and forth.
 *
 * @author FMRGJ
 */
@Slf4j
@Converter
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

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
		if (y == null) {
			return null;
		}
		
		return y.toString();
	}

}