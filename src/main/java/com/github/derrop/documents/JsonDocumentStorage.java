package com.github.derrop.documents;

import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;

public class JsonDocumentStorage implements DocumentStorage {

    public void write(Document document, Writer writer) {
        DefaultDocument.GSON.toJson(document.toJsonObject(), writer);
    }

    public DefaultDocument read(Reader reader) {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return new DefaultDocument().append(JsonParser.parseReader(bufferedReader).getAsJsonObject());
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }

}
