package com.github.derrop.documents.storage;

import com.github.derrop.documents.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public interface DocumentStorage {

    default Document read(InputStream inputStream) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return this.read(inputStreamReader);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    default Document read(Path path) {
        try (InputStream stream = Files.newInputStream(path)) {
            return this.read(stream);
        } catch (final IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    default Document read(File file) {
        return this.read(file.toPath());
    }

    default Document read(byte[] bytes) {
        return this.read(new ByteArrayInputStream(bytes));
    }

    default Document read(String input) {
        return this.read(new StringReader(input));
    }

    Document read(Reader reader);

    default void write(Document document, OutputStream outputStream) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            this.write(document, outputStreamWriter);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    default void write(Document document, File file) {
        this.write(document, file.toPath());
    }

    default void write(Document document, Path path) {
        Path parent = path.getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (OutputStream stream = Files.newOutputStream(path)) {
                this.write(document, stream);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    default String toString(Document document) {
        try (StringWriter writer = new StringWriter()) {
            this.write(document, writer);
            return writer.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    void write(Document document, Writer writer);

}
