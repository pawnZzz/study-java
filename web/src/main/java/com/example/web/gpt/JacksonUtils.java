package com.example.web.gpt;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for Jackson
 *
 * <p>This class provides utility methods to get a {@link JsonNodeFactory} and
 * a preconfigured {@link ObjectReader}. It can also be used to return
 * preconfigured instances of {@link ObjectMapper} (see {@link #newMapper()}.
 * </p>
 */
public final class JacksonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JacksonUtils.class);
    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    private static final ObjectReader READER;
    private static final ObjectWriter WRITER;
    private static final ObjectMapper mapper;

    static {
        mapper = newMapper();
        READER = mapper.reader();
        WRITER = mapper.writer();
    }

    private JacksonUtils() {
    }

    /**
     * Return a preconfigured {@link ObjectReader} to read JSON inputs
     *
     * @return the reader
     * @see #newMapper()
     */
    public static ObjectReader getReader() {
        return READER;
    }

    /**
     * Return a preconfigured {@link JsonNodeFactory} to generate JSON data as
     * {@link JsonNode}s
     *
     * @return the factory
     */
    public static JsonNodeFactory nodeFactory() {
        return FACTORY;
    }

    /**
     * Return a map out of an object's members
     *
     * <p>If the node given as an argument is not a map, an empty map is
     * returned.</p>
     *
     * @param node the node
     * @return a map
     */
    public static Map<String, JsonNode> asMap(final JsonNode node) {
        if (!node.isObject())
            return Collections.emptyMap();

        final Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        final Map<String, JsonNode> ret = Maps.newHashMap();

        Map.Entry<String, JsonNode> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            ret.put(entry.getKey(), entry.getValue());
        }

        return ret;
    }

    /**
     * Pretty print a JSON value
     *
     * @param node the JSON value to print
     * @return the pretty printed value as a string
     * @see #newMapper()
     */
    public static String prettyPrint(final JsonNode node) {
        final StringWriter writer = new StringWriter();

        try {
            WRITER.writeValue(writer, node);
            writer.flush();
        } catch (JsonGenerationException e) {
            throw new RuntimeException("How did I get there??", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("How did I get there??", e);
        } catch (IOException ignored) {
            // cannot happen
        }

        return writer.toString();
    }

    /**
     * Return a preconfigured {@link ObjectMapper}
     *
     * <p>The returned mapper will have the following features enabled:</p>
     *
     * <ul>
     *     <li>{@link DeserializationFeature#USE_BIG_DECIMAL_FOR_FLOATS};</li>
     *     <li>{@link SerializationFeature#WRITE_BIGDECIMAL_AS_PLAIN};</li>
     *     <li>{@link SerializationFeature#INDENT_OUTPUT}.</li>
     * </ul>
     *
     * <p>This returns a new instance each time.</p>
     *
     * @return an {@link ObjectMapper}
     */
    public static ObjectMapper newMapper() {
        return new ObjectMapper().setNodeFactory(FACTORY)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * 转换为字符串
     *
     * @param object
     * @return
     */
    public static <T> String toJson(T object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return object instanceof String ? (String) object : WRITER.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("toJson error ", e);
        }
        return null;
    }

    public static <T> T jsonToObject(String content, TypeReference<T> typeReference) throws Exception {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        T obj = mapper.readValue(content, typeReference);
        return obj;
    }

}