package org.no9.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.no9.lang.Predicate;

public class ObjectDSLArrayTest {
    public JsonNode JSON;
    public ObjectDSL jsonNode;

    @Before
    public void setup() throws Exception {
        JSON = ObjectDSL.parse("{\"name\": \"Bob\", \"children\": [{\"name\": \"Anne\", \"height\": 1.4}, {\"name\": \"John\", \"height\": 0.9}]}");
        jsonNode = ObjectDSL.from(JSON);
    }

    @Test
    public void should_count_2_children() {
        assertEquals(2, jsonNode.field("children").asStream().count());
    }

    @Test
    public void should_filter_children_who_are_taller_than_1_metre() {
        assertEquals(1, jsonNode.field("children").asStream().filter(new Predicate<ObjectDSL>() {
            @Override
            public boolean test(ObjectDSL fieldDSL) {
                return fieldDSL.field("height").asFloat() > 1.0;
            }
        }).count());
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_when_streaming_an_unknown_field() {
        assertEquals(2, jsonNode.field("parents").asStream().count());
    }
}
