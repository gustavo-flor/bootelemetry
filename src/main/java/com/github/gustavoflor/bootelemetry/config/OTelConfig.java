package com.github.gustavoflor.bootelemetry.config;

import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OTelConfig {

    @Bean
    public TextMapPropagator textMapPropagator() {
        return JaegerPropagator.getInstance();
    }

    @Bean
    public SpanExporter spanExporter() {
        return OtlpGrpcSpanExporter.getDefault();
    }

}
