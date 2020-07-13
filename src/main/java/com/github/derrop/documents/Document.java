package com.github.derrop.documents;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public interface Document {

    Collection<String> keys();

    int size();

    Document clear();

    Document remove(String key);

    boolean contains(String key);

    <T> T toInstanceOf(Class<T> clazz);

    <T> T toInstanceOf(Type type);

    Document append(String key, Object value);

    Document append(String key, Number value);

    Document append(String key, Boolean value);

    Document append(String key, String value);

    Document append(String key, Character value);

    Document append(String key, Document value);

    Document append(Document document);

    Document append(JsonObject jsonObject);

    Document append(Properties properties);

    Document append(String key, Properties properties);

    Document append(String key, byte[] bytes);

    Document append(Map<String, Object> map);

    Document getDocument(String key);

    Collection<Document> getDocuments(String key);

    int getInt(String key);

    double getDouble(String key);

    float getFloat(String key);

    byte getByte(String key);

    short getShort(String key);

    long getLong(String key);

    boolean getBoolean(String key);

    String getString(String key);

    char getChar(String key);

    BigDecimal getBigDecimal(String key);

    BigInteger getBigInteger(String key);

    JsonArray getJsonArray(String key);

    JsonObject getJsonObject(String key);

    Properties getProperties(String key);

    JsonElement get(String key);

    byte[] getBinary(String key);

    <T> T get(String key, Class<T> clazz);

    <T> T get(String key, Type type);

    <T> T get(String key, Gson gson, Class<T> clazz);

    <T> T get(String key, Gson gson, Type type);

    Integer getInt(String key, Integer def);

    Short getShort(String key, Short def);

    Boolean getBoolean(String key, Boolean def);

    Long getLong(String key, Long def);

    Double getDouble(String key, Double def);

    Float getFloat(String key, Float def);

    String getString(String key, String def);

    Document getDocument(String key, Document def);

    Collection<Document> getDocuments(String key, Collection<Document> def);

    JsonArray getJsonArray(String key, JsonArray def);

    JsonObject getJsonObject(String key, JsonObject def);

    byte[] getBinary(String key, byte[] def);

    <T> T get(String key, Type type, T def);

    <T> T get(String key, Class<T> clazz, T def);

    Properties getProperties(String key, Properties def);

    BigInteger getBigInteger(String key, BigInteger def);

    BigDecimal getBigDecimal(String key, BigDecimal def);

    Character getChar(String key, Character def);

}
