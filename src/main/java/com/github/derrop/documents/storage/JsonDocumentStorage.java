package com.github.derrop.documents.storage;

import com.github.derrop.documents.DefaultDocument;
import com.github.derrop.documents.Document;
import com.github.derrop.documents.Documents;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;

public class JsonDocumentStorage implements DocumentStorage {

    public void write(Document document, Writer writer) {
        DefaultDocument.GSON.toJson(document.toJsonObject(), writer);
    }

    public Document read(Reader reader) {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return Documents.newDocument(JsonParser.parseReader(bufferedReader).getAsJsonObject());
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }

}
