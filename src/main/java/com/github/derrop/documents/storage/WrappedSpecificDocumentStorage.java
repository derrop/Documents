package com.github.derrop.documents.storage;

import com.github.derrop.documents.Document;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;

public class WrappedSpecificDocumentStorage implements SpecificDocumentStorage {

    private final DocumentStorage storage;
    private final Document document;

    public WrappedSpecificDocumentStorage(Document document, DocumentStorage storage) {
        this.document = document;
        this.storage = storage;
    }

    @Override
    public Document document() {
        return this.document;
    }

    @Override
    public SpecificDocumentStorage write(OutputStream outputStream) {
        this.storage.write(this.document, outputStream);
        return this;
    }

    @Override
    public SpecificDocumentStorage write(File file) {
        this.storage.write(this.document, file);
        return this;
    }

    @Override
    public SpecificDocumentStorage write(Path path) {
        this.storage.write(this.document, path);
        return this;
    }

    @Override
    public String serializeToString() {
        return this.storage.toString(this.document);
    }

    @Override
    public SpecificDocumentStorage write(Writer writer) {
        this.storage.write(this.document, writer);
        return this;
    }
}
