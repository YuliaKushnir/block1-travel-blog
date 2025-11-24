package org.example.service.parsers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Post;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class JsonFileParser {

    public List<Post> parseJsonFile(File file) throws IOException {
        List<Post> posts = new ArrayList<>();

        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);

        try(JsonParser parser = factory.createParser(file)) {
            if(parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException(parser.getCurrentLocation() + " Очікувався: START_ARRAY. Отримано: " + parser.getCurrentToken());
            }

            while (parser.nextToken() == JsonToken.START_OBJECT) {
                posts.add(mapper.readValue(parser, Post.class));
            }

        }

        return posts;
    }
}
