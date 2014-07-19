package org.no9.util.json;

import org.no9.util.Optional;
import org.no9.util.stream.Stream;

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

    Stream<ObjectDSL> asStream();

    Stream<FieldDSL> asValueStream();
}
