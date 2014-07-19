package org.no9.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.no9.lang.Predicate;

public class ObjectDSLArrayValueTest {
    public JsonNode JSON;
    public ObjectDSL jsonNode;

    @Before
    public void setup() throws Exception {
        JSON = ObjectDSL.parse("{\"name\": \"Bob\", \"children\": [0.9, 1.0, 1.1, 1.2]}");
        jsonNode = ObjectDSL.from(JSON);
    }

    @Test
    public void should_count_4_children() {
        assertEquals(4, jsonNode.field("children").asValueStream().count());
    }

    @Test
    public void should_filter_children_who_are_taller_than_1_metre() {
        assertEquals(2, jsonNode.field("children").asValueStream().filter(new Predicate<FieldDSL>() {
            @Override
            public boolean test(FieldDSL fieldDSL) {
                return fieldDSL.asFloat() > 1.0;
            }
        }).count());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_when_streaming_an_unknown_field() {
        assertEquals(2, jsonNode.field("parents").asStream().count());
    }
}
