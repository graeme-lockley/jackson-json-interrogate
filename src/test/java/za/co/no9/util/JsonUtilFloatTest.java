package za.co.no9.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonUtilFloatTest {
    public static final float DEFAULT_FLOAT = (float) 83671.23;
    public static final float FLOAT_DELTA = (float) 0.0001;

    public JsonNode JSON;
    public JsonUtil jsonNode;

    @Before
    public void setup() throws Exception {
        JSON = JsonUtil.parse("{\"name\": \"Bob\", \"status\": {\"id\": 1, \"progress\": 12.3}}");
        jsonNode = JsonUtil.from(JSON);
    }

    @Test
    public void should_extract_float() throws Exception {
        assertEquals(12.3, jsonNode.field("status.progress").asFloat(), FLOAT_DELTA);
    }

    @Test
    public void should_extract_opt_float() throws Exception {
        assertEquals(12.3, jsonNode.field("status.progress").asOptFloat().get(), FLOAT_DELTA);
    }

    @Test
    public void should_return_empty_for_unknown_opt_float() throws Exception {
        assertTrue(jsonNode.field("status.unknown.float").asOptFloat().isNotPresent());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void should_throw_exception_for_illegal_asfloat() throws Exception {
        jsonNode.field("name").asFloat();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void should_throw_exception_for_illegal_asOptFloat() throws Exception {
        jsonNode.field("name").asOptFloat().get();
    }

    @Test
    public void should_return_default_for_illegal_float() throws Exception {
        assertEquals(DEFAULT_FLOAT, jsonNode.field("name").asFloat(DEFAULT_FLOAT), FLOAT_DELTA);
    }

    @Test
    public void should_return_default_for_illegal_opt_float() throws Exception {
        assertEquals(DEFAULT_FLOAT, jsonNode.field("name").asOptFloat(DEFAULT_FLOAT).get(), FLOAT_DELTA);
    }
}
