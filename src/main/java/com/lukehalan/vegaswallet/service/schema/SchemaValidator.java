package com.lukehalan.vegaswallet.service.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.lukehalan.vegaswallet.exception.JsonValidationException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.io.InputStream;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SchemaValidator {

  /**
   * This method is reading json schema file from resource
   *
   * @param path json schema path
   * @return Input Stream of Json file
   */
  private InputStream inputStreamFromClasspath(String path) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
  }

  /**
   * This method validate Json against schema
   *
   * @param schema Json schema path
   * @param jsonNode Json object
   * @throws JsonValidationException if validatoin failed
   */
  public void validate(String schema, JsonNode jsonNode) {
    JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
    InputStream schemaStream = inputStreamFromClasspath(schema);
    JsonSchema jsonSchema = schemaFactory.getSchema(schemaStream);
    Set<ValidationMessage> validationResult = jsonSchema.validate(jsonNode);

    if (validationResult.size() > 0) {
      throw new JsonValidationException(validationResult);
    }
  }
}
