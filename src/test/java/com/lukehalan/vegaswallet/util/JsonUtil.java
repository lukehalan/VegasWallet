package com.lukehalan.vegaswallet.util;

import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.lukehalan.vegaswallet.config.JacksonConfiguration;
import java.io.IOException;
import net.javacrumbs.jsonunit.fluent.JsonFluentAssert;

public class JsonUtil {

  public static ObjectMapper objectMapper =
          (new JacksonConfiguration()).objectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  public static String readJsonFile(String filename) throws IOException {
    return Resources.toString(Resources.getResource(filename), Charsets.UTF_8);
  }

  public static JsonNode readStringToJsonNode(String jsonString) throws JsonProcessingException {
    return objectMapper.readTree(jsonString);
  }

  public static void assertJsonMatch(Object actual, String expectedFile) {
    try {
      JsonFluentAssert.assertThatJson(objectMapper.writeValueAsString(actual))
          .isEqualTo(readJsonFile(expectedFile));
    } catch (AssertionError | Exception e) {
      e.printStackTrace();
      fail("failed to assert");
    }
  }
}
