package cs3500.pa04.model.jsonhandlers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "method-name": "method name",
 *   "arguments": {}
 * }
 * </code>
 * </p>
 *
 * @param messageName the name of the server method request
 * @param arguments   the arguments passed along with the message formatted as a Json object
 */
public record MessageJson(
    @JsonProperty("method-name") String messageName,
    @JsonProperty("arguments") JsonNode arguments) {
}