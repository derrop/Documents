# Documents

A library to simplify the usage of [Gson](https://github.com/google/gson) and [SnakeYaml](https://bitbucket.org/asomov/snakeyaml/src/master/).

## Getting started

Maven:

```xml
<repository>
    <id>jitpack</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.derrop</groupId>
    <artifactId>documents</artifactId>
    <version>1.0-RELEASE</version>
</dependency>
```

Gradle:

```groovy
maven {
    name 'jitpack'
    url 'https://jitpack.io'
}

compile group: 'com.github.derrop', name: 'documents', version: '1.0-RELEASE'
```
<br>

With that being done, you can now create your [Document](src/main/java/com/github/derrop/documents/Document.java) with the methods in the [Documents](src/main/java/com/github/derrop/documents/Documents.java) class.

Serialization and deserialization into strings/files can be done with the [DocumentStorage](src/main/java/com/github/derrop/documents/DocumentStorage.java), it can be accessed with the jsonStorage() and yamlStorage() methods in the [Documents](src/main/java/com/github/derrop/documents/Documents.java) class.
