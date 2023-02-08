package persistence;

import org.json.JSONObject;

// Inspiration and template taken from JsonSerializationDemo obtained from CPSC 210 EdX page

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
