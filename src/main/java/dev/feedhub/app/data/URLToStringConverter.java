package dev.feedhub.app.data;

import java.net.URL;

import org.springframework.lang.NonNull;

public class URLToStringConverter implements org.springframework.core.convert.converter.Converter<java.net.URL, String> {
    
    @Override
    public String convert(@NonNull URL source) {
        return source.toString();
    }

}
