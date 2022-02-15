package com.kostenko.youtubeanalyticservice.service;

import com.kostenko.youtubeanalyticservice.entity.JSONKeys;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {
    @Test
    void parseTitleTest() {
        JSONObject jsonObject = new JSONObject(String.format("{'%s': 'title value'}", JSONKeys.TITLE.getKey()));
        String actual = JSONParser.parseTitle(jsonObject);
        String expected = "title value";
        assertEquals(expected, actual);
    }

    @Test
    void parseDescriptionTest() {
        JSONObject jsonObject = new JSONObject(String.format("{'%s': 'description value'}", JSONKeys.DESCRIPTION.getKey()));
        String actual = JSONParser.parseDescription(jsonObject);
        String expected = "description value";
        assertEquals(expected, actual);
    }

    @Test
    void parseCountryTest() {
        JSONObject jsonObject = new JSONObject(String.format("{'%s': 'UA'}", JSONKeys.COUNTRY.getKey()));
        String actual = JSONParser.parseCountry(jsonObject);
        String expected = "UA";
        assertEquals(expected, actual);
    }

    @Test
    void parsePublishedAtTest() {
        JSONObject jsonObject = new JSONObject(String.format("{'%s': '2020-09-13T12:26:40Z'}", JSONKeys.PUBLISHED_AT.getKey()));
        Date actual = JSONParser.parsePublishedAt(jsonObject);
        Date expected = new Date(1_600_000_000_000L);
        assertEquals(expected, actual);
    }

    @Test
    void parseItemsTest() {
        JSONArray actual = JSONParser.parseItems(String.format("{'%s': [{'any_key': 'any_value'}]}", JSONKeys.ITEMS.getKey()));
        JSONArray expected = new JSONArray("[{'any_key': 'any_value'}]");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void parseEmptyItemsTest() {
        JSONArray actual = JSONParser.parseItems("{'Any other key': [{'any_key': 'any_value'}]}");
        JSONArray expected = new JSONArray("[]");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void parseSnippet() {
        JSONObject jsonObject = new JSONObject(String.format("{'%s': {'any_key': 'any_value'}}", JSONKeys.SNIPPET.getKey()));
        JSONObject actual = JSONParser.parseSnippet(jsonObject);
        JSONObject expected = new JSONObject("{'any_key': 'any_value'}");
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void parseVideoId() {
        JSONObject jsonObject = new JSONObject(
                String.format(
                        "{'%s': {'%s': 'Video id value'}}",
                        JSONKeys.ID.getKey(),
                        JSONKeys.VIDEO_ID.getKey()
                )
        );
        String actual = JSONParser.parseVideoId(jsonObject);
        String expected = "Video id value";
        assertEquals(expected, actual);
    }
}
