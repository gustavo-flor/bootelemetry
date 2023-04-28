package com.github.gustavoflor.bootelemetry.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "otel")
public class OTelProperties {

    private String spanExporterEndpoint;

}
