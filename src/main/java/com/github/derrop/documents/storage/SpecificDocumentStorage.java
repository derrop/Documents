package com.github.derrop.documents.storage;

import com.github.derrop.documents.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public interface SpecificDocumentStorage {

    Document document();

    SpecificDocumentStorage write(OutputStream outputStream);

    SpecificDocumentStorage write(File file);

    SpecificDocumentStorage write(Path path);

    String serializeToString();

    SpecificDocumentStorage write(Writer writer);

}
