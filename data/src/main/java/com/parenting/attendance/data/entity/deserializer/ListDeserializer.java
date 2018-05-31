package com.parenting.attendance.data.entity.deserializer;

import com.parenting.attendance.data.entity.CryptoList;
import com.parenting.attendance.data.entity.LTC;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by adwait on 17/11/17.
 */

public class ListDeserializer implements JsonDeserializer<CryptoList> {

    @Override
    public CryptoList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject menu = json.getAsJsonObject().getAsJsonObject("data");
        Set<Map.Entry<String,JsonElement>> set = menu.entrySet();
        Map<String,LTC> map = new HashMap<String,LTC>();
        Iterator iter = set.iterator();//menu.keys();
        while(iter.hasNext()){
            Map.Entry<String,JsonElement> entry = (Map.Entry<String, JsonElement>) iter.next();
            map.put(entry.getKey(),context.deserialize(entry.getValue(),LTC.class));
        }
        return new CryptoList(new ArrayList<LTC>(map.values()));
    }
}
