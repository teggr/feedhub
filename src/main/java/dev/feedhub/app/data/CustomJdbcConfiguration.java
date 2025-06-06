package dev.feedhub.app.data;

import java.util.List;

import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.stereotype.Component;

@Component
public class CustomJdbcConfiguration extends AbstractJdbcConfiguration {

    @SuppressWarnings("null")
    @Override
    protected List<?> userConverters() {
        return List.of(
            new URLToStringConverter()
        );
    }

}
