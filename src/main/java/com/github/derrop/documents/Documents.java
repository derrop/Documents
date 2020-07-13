package com.github.derrop.documents;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Documents {

    private static final DocumentStorage JSON = new JsonDocumentStorage();
    private static final DocumentStorage YAML = new YamlDocumentStorage();

    public static Document newDocument() {
        return new DefaultDocument();
    }

    public static Document newDocument(JsonElement jsonElement) {
        return new DefaultDocument(jsonElement);
    }

    public static Document newDocument(JsonObject jsonObject) {
        return new DefaultDocument(jsonObject);
    }

    public static Document newDocument(String key, String value) {
        return new DefaultDocument(key, value);
    }

    public static Document newDocument(String key, Number value) {
        return new DefaultDocument(key, value);
    }

    public static Document newDocument(String key, Character value) {
        return new DefaultDocument(key, value);
    }

    public static Document newDocument(String key, Boolean value) {
        return new DefaultDocument(key, value);
    }

    public static Document newDocument(String key, Object value) {
        return new DefaultDocument(key, value);
    }

    public static Document newDocument(Object object) {
        return new DefaultDocument(DefaultDocument.GSON.toJsonTree(object));
    }

    public static Document newJsonDocument(byte[] bytes) {
        return newJsonDocument(new String(bytes, StandardCharsets.UTF_8));
    }

    public static Document newYamlDocument(byte[] bytes) {
        return newYamlDocument(new String(bytes, StandardCharsets.UTF_8));
    }

    public static Document newJsonDocument(Path path) {
        return jsonStorage().read(path);
    }

    public static Document newYamlDocument(Path path) {
        return yamlStorage().read(path);
    }

    public static Document newJsonDocument(String input) {
        return jsonStorage().read(input);
    }

    public static Document newYamlDocument(String input) {
        return yamlStorage().read(input);
    }

    public static DocumentStorage jsonStorage() {
        return JSON;
    }

    public static DocumentStorage yamlStorage() {
        return YAML;
    }

}
