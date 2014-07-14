package za.co.no9.util;

public interface FieldDSL {
    String asString();

    Optional<String> asOptString();

    int asInt();

    Optional<Integer> asOptInt();

    int asInt(int defaultInt);

    Optional<Integer> asOptInt(int defaultInt);

    float asFloat();

    Optional<Float> asOptFloat();

    float asFloat(float defaultFloat);

    Optional<Float> asOptFloat(float defaultFloat);
}
