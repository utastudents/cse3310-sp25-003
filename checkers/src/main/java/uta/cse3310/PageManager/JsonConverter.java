package uta.cse3310.PageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonConverter {
    
    
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    
 
      
     
    public static String toJson(Object object) {
        // convert Java object to JSON string
        return null;
    }
    
    

  
     
    public static UserEvent parseUserEvent(String json) {
        // parse JSON to UserEvent
        return null;
    }
    
    
 

     
    public static JsonObject parseJsonObject(String json) {
        // parse JSON to JsonObject
        return null;
    }
    
    
      
     
     
    public static String extractField(String json, String fieldName) {
        // xtract field from JSON
        return null;
    }
    
    
    
     
    public static UserEventReply createErrorReply(String errorMessage, Integer userId) {
        //create an error reply
        return null;
    }
    
    
    
     
    public static UserEventReply createSuccessReply(String type, String message, Integer userId) {
        // create a success reply
        return null;
    }
} 