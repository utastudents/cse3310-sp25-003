package uta.cse3310.PageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement;
import java.util.ArrayList;

public class JsonConverter {

    // Private Gson instance used for all conversions
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Public getter to allow safe access to gson elsewhere (e.g. PageManager)
    public static Gson getGson() {
        return gson;
    }

    // Convert Java object to JSON string
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    // Convert JSON string to UserEvent object
    public static UserEvent parseUserEvent(String json) {
        try {
            return gson.fromJson(json, UserEvent.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing UserEvent JSON: " + e.getMessage());
            return null;
        }
    }

    public static LoginPayload parseLoginPayload(String json) {
        try {
            return gson.fromJson(json, LoginPayload.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing UserEvent JSON: " + e.getMessage());
            return null;
        }
    }

    public static SignupPayload parseSigupPayload(String json) {
        try {
            return gson.fromJson(json, SignupPayload.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing UserEvent JSON: " + e.getMessage());
            return null;
        }
    }

    // Parse raw JSON string to JsonObject
    public static JsonObject parseJsonObject(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonObject();
        } catch (Exception e) {
            System.err.println("Error parsing JSON to JsonObject: " + e.getMessage());
            return null;
        }
    }

    // Extract a specific field value from JSON by field name
    public static String extractField(String json, String fieldName) {
        try {
            JsonObject jsonObject = parseJsonObject(json);
            if (jsonObject != null && jsonObject.has(fieldName)) {
                JsonElement element = jsonObject.get(fieldName);
                if (element.isJsonPrimitive()) {
                    return element.getAsString();
                }
                return element.toString();
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error extracting field from JSON: " + e.getMessage());
            return null;
        }
    }

    // Build a reply object for error messages
    public static UserEventReply createErrorReply(String errorMessage, Integer userId) {
        UserEventReply reply = new UserEventReply();
        reply.setMessage(errorMessage);
        reply.setSuccess(false);

        ArrayList<Integer> recipients = new ArrayList<>();
        recipients.add(userId);
        reply.setRecipients(recipients);

        return reply;
    }

    // Build a reply object for success responses
    public static UserEventReply createSuccessReply(String type, String message, Integer userId) {
        UserEventReply reply = new UserEventReply();
        reply.setMessage(message);
        reply.setSuccess(true);
        reply.setType(type);

        ArrayList<Integer> recipients = new ArrayList<>();
        recipients.add(userId);
        reply.setRecipients(recipients);

        return reply;
    }
}
