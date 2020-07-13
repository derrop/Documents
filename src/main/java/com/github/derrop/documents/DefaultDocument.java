package com.github.derrop.documents;

import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DefaultDocument implements Document {

    public static final TypeAdapter<DefaultDocument> TYPE_ADAPTER = new TypeAdapter<DefaultDocument>() {
        @Override
        public void write(JsonWriter jsonWriter, DefaultDocument document) throws IOException {
            TypeAdapters.JSON_ELEMENT.write(jsonWriter, document == null ? new JsonObject() : document.jsonObject);
        }

        @Override
        public DefaultDocument read(JsonReader jsonReader) throws IOException {
            JsonElement jsonElement = TypeAdapters.JSON_ELEMENT.read(jsonReader);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                return new DefaultDocument(jsonElement);
            } else {
                return null;
            }
        }
    };

    public static Gson GSON = new GsonBuilder()
            .serializeNulls()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(TypeAdapters.newTypeHierarchyFactory(DefaultDocument.class, TYPE_ADAPTER))
            .create();

    protected final JsonObject jsonObject;

    public DefaultDocument(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public DefaultDocument() {
        this(new JsonObject());
    }

    public DefaultDocument(Object toObjectMirror) {
        this(GSON.toJsonTree(toObjectMirror));
    }

    public DefaultDocument(JsonElement jsonElement) {
        this(jsonElement.getAsJsonObject());
    }

    public DefaultDocument(Properties properties) {
        this();
        this.append(properties);
    }

    public DefaultDocument(String key, String value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, Object value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, Boolean value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, Number value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, Character value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, DefaultDocument value) {
        this();
        this.append(key, value);
    }

    public DefaultDocument(String key, Properties value) {
        this();
        this.append(key, value);
    }

    public Collection<String> keys() {
        Collection<String> collection = new ArrayList<>(this.jsonObject.size());

        for (Map.Entry<String, JsonElement> entry : this.jsonObject.entrySet()) {
            collection.add(entry.getKey());
        }

        return collection;
    }
    
    public int size() {
        return this.jsonObject.size();
    }
    
    public DefaultDocument clear() {
        for (Map.Entry<String, JsonElement> elementEntry : this.jsonObject.entrySet()) {
            this.jsonObject.remove(elementEntry.getKey());
        }

        return this;
    }
    
    public DefaultDocument remove(String key) {
        this.jsonObject.remove(key);
        return this;
    }
    
    public boolean contains(String key) {
        return key != null && this.jsonObject.has(key);
    }
    
    public <T> T toInstanceOf(Class<T> clazz) {
        return GSON.fromJson(jsonObject, clazz);
    }
    
    public <T> T toInstanceOf(Type type) {
        return GSON.fromJson(jsonObject, type);
    }
    
    public DefaultDocument append(String key, Object value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.add(key, GSON.toJsonTree(value));
        return this;
    }
    
    public DefaultDocument append(String key, Number value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.addProperty(key, value);
        return this;
    }
    
    public DefaultDocument append(String key, Boolean value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.addProperty(key, value);
        return this;
    }
    
    public DefaultDocument append(String key, String value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.addProperty(key, value);
        return this;
    }

    public DefaultDocument append(String key, Character value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.addProperty(key, value);
        return this;
    }

    public DefaultDocument append(String key, Document value) {
        if (key == null || value == null) {
            return this;
        }

        this.jsonObject.add(key, ((DefaultDocument) value).jsonObject);
        return this;
    }

    public DefaultDocument append(Document document) {
        if (document == null) {
            return this;
        } else {
            return append(((DefaultDocument) document).jsonObject);
        }
    }

    public DefaultDocument append(JsonObject jsonObject) {
        if (jsonObject == null) {
            return this;
        }

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            this.jsonObject.add(entry.getKey(), entry.getValue());
        }

        return this;
    }

    public DefaultDocument append(Properties properties) {
        if (properties == null) {
            return this;
        }

        Object entry;
        Enumeration<?> enumeration = properties.keys();

        while (enumeration.hasMoreElements() && (entry = enumeration.nextElement()) != null) {
            append(entry.toString(), properties.getProperty(entry.toString()));
        }

        return this;
    }

    public DefaultDocument append(String key, Properties properties) {
        return append(key, new DefaultDocument(properties));
    }

    public DefaultDocument append(String key, byte[] bytes) {
        if (key == null || bytes == null) {
            return this;
        }

        return this.append(key, Base64.getEncoder().encodeToString(bytes));
    }

    public DefaultDocument append(Map<String, Object> map) {
        if (map == null) {
            return this;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            this.append(entry.getKey(), entry.getValue());
        }

        return this;
    }

    public DefaultDocument append(InputStream inputStream) {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return append(reader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this;
    }

    public DefaultDocument append(Reader reader) {
        return append(JsonParser.parseReader(reader).getAsJsonObject());
    }

    public DefaultDocument getDocument(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonObject()) {
            return new DefaultDocument(jsonElement);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Document> getDocuments(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            Collection<Document> documents = new ArrayList<>();
            for (JsonElement element : array) {
                if (element.isJsonObject()) {
                    documents.add(new DefaultDocument(element.getAsJsonObject()));
                }
            }
            return documents;
        }

        return null;
    }

    public int getInt(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsInt();
        } else {
            return 0;
        }
    }

    public double getDouble(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsDouble();
        } else {
            return 0;
        }
    }
    
    public float getFloat(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsFloat();
        } else {
            return 0;
        }
    }
    
    public byte getByte(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsByte();
        } else {
            return 0;
        }
    }
    
    public short getShort(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsShort();
        } else {
            return 0;
        }
    }

    public long getLong(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsLong();
        } else {
            return 0;
        }
    }

    public boolean getBoolean(String key) {
        if (!contains(key)) {
            return false;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        } else {
            return false;
        }
    }

    public String getString(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        } else {
            return null;
        }
    }

    public char getChar(String key) {
        if (!contains(key)) {
            return 0;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString().charAt(0);
        } else {
            return 0;
        }
    }

    public BigDecimal getBigDecimal(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBigDecimal();
        } else {
            return null;
        }
    }

    public BigInteger getBigInteger(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBigInteger();
        } else {
            return null;
        }
    }

    public JsonArray getJsonArray(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else {
            return null;
        }
    }

    public JsonObject getJsonObject(String key) {
        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = this.jsonObject.get(key);

        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        } else {
            return null;
        }
    }

    public Properties getProperties(String key) {
        Properties properties = new Properties();

        for (Map.Entry<String, JsonElement> entry : this.jsonObject.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue().toString());
        }

        return properties;
    }

    public JsonElement get(String key) {
        if (!contains(key)) {
            return null;
        }

        return this.jsonObject.get(key);
    }

    public byte[] getBinary(String key) {
        return Base64.getDecoder().decode(this.getString(key));
    }

    public <T> T get(String key, Class<T> clazz) {
        return this.get(key, GSON, clazz);
    }

    public <T> T get(String key, Type type) {
        return this.get(key, GSON, type);
    }

    public <T> T get(String key, Gson gson, Class<T> clazz) {
        if (key == null || gson == null || clazz == null) {
            return null;
        }

        JsonElement jsonElement = get(key);

        if (jsonElement == null) {
            return null;
        } else {
            return gson.fromJson(jsonElement, clazz);
        }
    }

    public <T> T get(String key, Gson gson, Type type) {
        if (key == null || gson == null || type == null) {
            return null;
        }

        if (!contains(key)) {
            return null;
        }

        JsonElement jsonElement = get(key);

        if (jsonElement == null) {
            return null;
        } else {
            return gson.fromJson(jsonElement, type);
        }
    }

    public Integer getInt(String key, Integer def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getInt(key);
    }

    public Short getShort(String key, Short def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getShort(key);
    }

    public Boolean getBoolean(String key, Boolean def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getBoolean(key);
    }

    public Long getLong(String key, Long def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getLong(key);
    }

    public Double getDouble(String key, Double def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getDouble(key);
    }


    public Float getFloat(String key, Float def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getFloat(key);
    }

    public String getString(String key, String def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getString(key);
    }

    public DefaultDocument getDocument(String key, Document def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getDocument(key);
    }

    @Override
    public Collection<Document> getDocuments(String key, Collection<Document> def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getDocuments(key);
    }

    public JsonArray getJsonArray(String key, JsonArray def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getJsonArray(key);
    }

    public JsonObject getJsonObject(String key, JsonObject def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getJsonObject(key);
    }

    public byte[] getBinary(String key, byte[] def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getBinary(key);
    }


    public <T> T get(String key, Type type, T def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.get(key, type);
    }

    public <T> T get(String key, Class<T> clazz, T def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.get(key, clazz);
    }

    public Properties getProperties(String key, Properties def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getProperties(key);
    }

    public BigInteger getBigInteger(String key, BigInteger def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getBigInteger(key);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getBigDecimal(key);
    }

    public Character getChar(String key, Character def) {
        if (!this.contains(key)) {
            this.append(key, def);
        }

        return this.getChar(key);
    }

    public JsonObject toJsonObject() {
        return jsonObject;
    }

    public String toPrettyJson() {
        return GSON.toJson(this.jsonObject);
    }

    public String toJson() {
        return this.jsonObject.toString();
    }

    public byte[] toByteArray() {
        return toJson().getBytes(StandardCharsets.UTF_8);
    }
    
    public String toString() {
        return toJson();
    }
    
    public Iterator<String> iterator() {
        return this.jsonObject.keySet().iterator();
    }
}
