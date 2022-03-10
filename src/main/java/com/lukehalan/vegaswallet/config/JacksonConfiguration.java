package com.lukehalan.vegaswallet.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JacksonConfiguration {

  /**
   * Creates a new object mapper bean.
   *
   * @return Object mapper bean
   */
  private <T extends ObjectMapper> T configureObjectMapper(T mapper) {
    mapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
    mapper.findAndRegisterModules();
    mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    mapper.configure(Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(formatLocalDate());
    mapper.registerModule(formatLocalDateTime());
    mapper.registerModule(formatZoneDateTime());
    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    return mapper;
  }


  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = configureObjectMapper(new ObjectMapper());
    return objectMapper;
  }



  private SimpleModule formatZoneDateTime() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(
        ZonedDateTime.class,
        new JsonSerializer<ZonedDateTime>() {
          @Override
          public void serialize(
              ZonedDateTime zonedDateTime,
              JsonGenerator jsonGenerator,
              SerializerProvider serializerProvider)
              throws IOException {
            zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
            jsonGenerator.writeString(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
                    .format(zonedDateTime));
          }
        });
    return module;
  }

  private JavaTimeModule formatLocalDate() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    LocalDateSerializer dateSerializer = new LocalDateSerializer(dateTimeFormatter);
    LocalDateDeserializer dateDeserializer = new LocalDateDeserializer(dateTimeFormatter);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDate.class, dateSerializer);
    javaTimeModule.addDeserializer(LocalDate.class, dateDeserializer);
    return javaTimeModule;
  }

  private JavaTimeModule formatLocalDateTime() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(dateTimeFormatter);
    LocalDateTimeDeserializer dateTimeDeserializer =
        new LocalDateTimeDeserializer(dateTimeFormatter);
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);
    javaTimeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    return javaTimeModule;
  }
}
