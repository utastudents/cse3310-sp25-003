package uta.cse3310.PageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement;
import java.util.ArrayList;


public class JsonConverter {
    
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
    public static String toJson(Object object) {
        return gson.toJson(object);
    }
    
    
    public static UserEvent parseUserEvent(String json) {
        try {
            return gson.fromJson(json, UserEvent.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing UserEvent JSON: " + e.getMessage());
            return null;
        }
    }
    
   
    public static JsonObject parseJsonObject(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonObject();
        } catch (Exception e) {
            System.err.println("Error parsing JSON to JsonObject: " + e.getMessage());
            return null;
        }
    }
    
    
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
    
   
    public static UserEventReply createErrorReply(String errorMessage, Integer userId) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.status.message = errorMessage;
        reply.status.success = false;
        
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userId);
        
        return reply;
    }
    
    
    public static UserEventReply createSuccessReply(String type, String message, Integer userId) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.status.message = message;
        reply.status.success = true;
        reply.type = type;
        
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userId);
        
        return reply;
    }
} 