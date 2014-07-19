package org.no9.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObjectDSLTest {
    public static final int DEFAULT_INT = 9987;

    public JsonNode JSON;
    public ObjectDSL jsonNode;

    @Before
    public void setup() throws Exception {
        JSON = ObjectDSL.parse("{\"name\": \"Bob\", \"status\": {\"id\": 1, \"progress\": 12.3}}");
        jsonNode = ObjectDSL.from(JSON);
    }

    @Test
    public void should_extract_name() throws Exception {
        assertEquals("Bob", jsonNode.field("name").asString());
    }

    @Test
    public void should_return_an_empty_optional_when_field_not_found_for_asOptString() throws Exception {
        assertTrue(jsonNode.field("title").asOptString().isNotPresent());
    }

    @Test
    public void should_return_an_empty_optional_when_nested_interior_field_not_found_for_asOptString() throws Exception {
        assertTrue(jsonNode.field("title.id").asOptString().isNotPresent());
    }

    @Test
    public void should_return_an_empty_optional_when_nested_final_field_not_found_for_asOptString() throws Exception {
        assertTrue(jsonNode.field("status.name").asOptString().isNotPresent());
    }

    @Test
    public void should_return_an_empty_optional_when_nested_interior_field_not_found_for_asOptInt_even_when_a_default_is_passed() throws Exception {
        assertTrue(jsonNode.field("status.name").asOptInt(DEFAULT_INT).isNotPresent());
    }

    @Test
    public void should_return_an_empty_optional_when_nested_final_field_not_found_for_asOptInt_even_when_a_default_is_passed() throws Exception {
        assertTrue(jsonNode.field("status.name").asOptInt(DEFAULT_INT).isNotPresent());
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void should_throw_null_pointer_exception_when_field_not_found_for_asString() throws Exception {
        jsonNode.field("title").asString();
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void should_throw_null_pointer_exception_when_nested_field_not_found_for_asString() throws Exception {
        jsonNode.field("status.xxx").asString();
    }

    @Test
    public void should_extract_nested_id() throws Exception {
        assertEquals("1", jsonNode.field("status.id").asString());
    }

    @Test
    public void should_extract_int() throws Exception {
        assertEquals(1, jsonNode.field("status.id").asInt());
    }

    @Test
    public void should_return_default_when_int_field_does_not_exist() throws Exception {
        assertEquals(DEFAULT_INT, jsonNode.field("status.xxx").asInt(DEFAULT_INT));
    }

    @Test
    public void should_return_default_when_int_field_format_is_incorrect() throws Exception {
        assertEquals(DEFAULT_INT, jsonNode.field("name").asInt(DEFAULT_INT));
    }

    @Test
    public void should_return_default_when_opt_int_field_format_is_incorrect() throws Exception {
        assertEquals(DEFAULT_INT, (long) jsonNode.field("name").asOptInt(DEFAULT_INT).get());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException_for_get_invalid_int() throws Exception {
        jsonNode.field("name").asInt();
    }

    @Test
    public void should_extract_opt_int() throws Exception {
        assertEquals(new Integer(1), jsonNode.field("status.id").asOptInt().get());
    }
}
