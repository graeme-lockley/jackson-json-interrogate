package za.co.no9.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import za.co.no9.util.Optional;
import za.co.no9.util.function.Function;
import za.co.no9.util.stream.Stream;

import java.io.IOException;

public class ObjectDSL {
    private final JsonNode json;

    public ObjectDSL(JsonNode json) {
        this.json = json;
    }

    public static JsonNode parse(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(jsonString);
    }

    public static ObjectDSL from(JsonNode json) {
        return new ObjectDSL(json);
    }

    public FieldDSL field(String name) {
        if (name.contains(".")) {
            return getNestedFieldDSL(name);
        } else {
            return new ValueDSL(json, name);
        }
    }

    private FieldDSL getNestedFieldDSL(String name) {
        String[] fieldNames = StringUtils.split(name, ".");

        JsonNode current = json;
        int fieldIdx = 0;
        while (true) {
            if (fieldIdx == fieldNames.length - 1) {
                return new ValueDSL(current, fieldNames[fieldIdx]);
            } else {
                current = current.get(fieldNames[fieldIdx]);
                if (current == null) {
                    return new EmptyDSL(name);
                } else {
                    fieldIdx += 1;
                }
            }
        }
    }

    public static class ValueDSL implements FieldDSL {
        protected final JsonNode json;

        public ValueDSL(JsonNode json, String fieldName) {
            this.json = json.get(fieldName);
        }

        public ValueDSL(JsonNode json) {
            this.json = json;
        }

        public String asString() {
            return asOptString().get();
        }

        public Optional<String> asOptString() {
            return json == null ? Optional.<String>empty() : Optional.of(json.asText());
        }

        @Override
        public int asInt() {
            return asOptInt().get();
        }

        @Override
        public Optional<Integer> asOptInt() {
            return json == null ? Optional.<Integer>empty() : Optional.of(new Integer(json.asText()));
        }

        @Override
        public int asInt(int defaultInt) {
            try {
                return asOptInt().orElse(defaultInt);
            } catch (IllegalArgumentException ignored) {
                return defaultInt;
            }
        }

        @Override
        public Optional<Integer> asOptInt(int defaultInt) {
            try {
                return asOptInt();
            } catch (IllegalArgumentException ignored) {
                return Optional.of(defaultInt);
            }
        }

        @Override
        public float asFloat() {
            return asOptFloat().get();
        }

        @Override
        public Optional<Float> asOptFloat() {
            return json == null ? Optional.<Float>empty() : Optional.of(new Float(json.asText()));
        }

        @Override
        public float asFloat(float defaultFloat) {
            try {
                return asOptFloat().orElse(defaultFloat);
            } catch (IllegalArgumentException ignored) {
                return defaultFloat;
            }
        }

        @Override
        public Optional<Float> asOptFloat(float defaultFloat) {
            try {
                return asOptFloat();
            } catch (IllegalArgumentException ignored) {
                return Optional.of(defaultFloat);
            }
        }

        @Override
        public Stream<ObjectDSL> asStream() {
            return Stream.create(json.elements()).map(new Function<JsonNode, ObjectDSL>() {
                @Override
                public ObjectDSL apply(JsonNode jsonNode) {
                    return new ObjectDSL(jsonNode);
                }
            });
        }
    }

    public static class EmptyDSL implements FieldDSL {
        private final String name;

        public EmptyDSL(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.throwNullPointerException();
        }

        private <X> X throwNullPointerException() {
            throw new NullPointerException("The fields " + name + " do not resolve to a value.");
        }

        @Override
        public Optional<String> asOptString() {
            return Optional.empty();
        }

        @Override
        public int asInt() {
            return this.<Integer>throwNullPointerException();
        }

        @Override
        public Optional<Integer> asOptInt() {
            return Optional.empty();
        }

        @Override
        public int asInt(int defaultInt) {
            return defaultInt;
        }

        @Override
        public Optional<Integer> asOptInt(int defaultInt) {
            return Optional.empty();
        }

        @Override
        public float asFloat() {
            return this.<Float>throwNullPointerException();
        }

        @Override
        public Optional<Float> asOptFloat() {
            return Optional.empty();
        }

        @Override
        public float asFloat(float defaultFloat) {
            return defaultFloat;
        }

        @Override
        public Optional<Float> asOptFloat(float defaultFloat) {
            return Optional.empty();
        }

        @Override
        public Stream<ObjectDSL> asStream() {
            return this.throwNullPointerException();
        }
    }
}
