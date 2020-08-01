package com.github.derrop.documents.storage;

import com.github.derrop.documents.DefaultDocument;
import com.github.derrop.documents.Document;
import com.google.gson.JsonElement;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlDocumentStorage implements DocumentStorage {

    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(new Constructor(), new Representer(), options);
    });

    @SuppressWarnings("unchecked")
    @Override
    public Document read(Reader reader) {
        Map<String, Object> map = yaml.get().loadAs(reader, LinkedHashMap.class);
        JsonElement element = DefaultDocument.GSON.toJsonTree(map);
        return new DefaultDocument(element);
    }

    @Override
    public void write(Document document, Writer writer) {
        yaml.get().dump(document.toPlainObjects(), writer);
    }

}
