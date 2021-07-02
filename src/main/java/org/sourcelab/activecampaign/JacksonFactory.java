/**
 * Copyright 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/activecampaign-java-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.sourcelab.activecampaign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;

/**
 * Creates properly configured Jackson Json Mapper instances.
 */
public final class JacksonFactory {

    /**
     * Holds our jackson singleton mapper.  ObjectMapper is defined as being
     * ThreadSafe so this should be OK to stash as a static and shared.
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Defines a "type safe" Map[String, String] deserialization type for Jackson.
     */
    public static final MapType mapTypeStringString = mapper.getTypeFactory()
        .constructMapType(Map.class, String.class, String.class);

    /*
     * Statically configure the instance.
     */
    static {
        // Configure mapper
        mapper
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)

            // Deserialize empty array to null
            .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)

            // Deserialize into Jdk8 ZonedDateTime
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // Dont adjust to local timezone.
            .disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    /**
     * Creates properly configured Jackson Object Mapper instances.
     * @return ObjectMapper instance.
     */
    public static ObjectMapper newInstance() {
        return mapper;
    }
}