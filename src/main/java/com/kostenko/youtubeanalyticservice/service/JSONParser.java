package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.entity.JSONKeys;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@UtilityClass
public class JSONParser {
    public String parseTitle(JSONObject jsonObject) {
        return jsonObject.getString(JSONKeys.TITLE.getKey());
    }

    public String parseDescription(JSONObject jsonObject) {
        return jsonObject.getString(JSONKeys.DESCRIPTION.getKey());
    }

    public String parseCountry(JSONObject jsonObject) {
        return jsonObject.getString(JSONKeys.COUNTRY.getKey());
    }

    public Date parsePublishedAt(JSONObject jsonObject) {
        String date = jsonObject.getString(JSONKeys.PUBLISHED_AT.getKey());
        TemporalAccessor accessor = DateTimeFormatter.ISO_DATE_TIME.parse(date);
        Instant instant = Instant.from(accessor);
        return Date.from(instant);
    }

    public JSONArray parseItems(String string) {
        JSONObject jsonObject = new JSONObject(string);
        return jsonObject.has(JSONKeys.ITEMS.getKey())
                ? jsonObject.getJSONArray(JSONKeys.ITEMS.getKey())
                : new JSONArray();
    }

    public JSONObject parseSnippet(JSONObject jsonObject) {
        return jsonObject.getJSONObject(JSONKeys.SNIPPET.getKey());
    }

    public String parseVideoId(JSONObject jsonObject) {
        JSONObject id = jsonObject.getJSONObject(JSONKeys.ID.getKey());
        return id.getString(JSONKeys.VIDEO_ID.getKey());
    }
}
