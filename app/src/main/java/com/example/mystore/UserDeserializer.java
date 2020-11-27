package com.example.mystore;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserDeserializer implements JsonDeserializer<Features> {

    @Override
    public Features deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Features features = new Features();
        JsonObject jsonObject = (JsonObject) json;
        for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()){
            String key = element.getKey();
            JsonObject obj = jsonObject.getAsJsonObject(key);
            Map<String, Integer> settingMaps = new HashMap<>();
            for (Map.Entry<String, JsonElement> setting : obj.entrySet()){
                String settingKey = setting.getKey();
                Integer integer = obj.get(settingKey).getAsInt();
                settingMaps.put(settingKey, integer);
            }
            features.add(key,settingMaps);
        }
        return features;
    }
}
