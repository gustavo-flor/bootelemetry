package com.github.gustavoflor.bootelemetry.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.extension.trace.propagation.JaegerPropagator;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class OTelConfig {

    private final OTelProperties oTelProperties;

    @Bean
    public TextMapPropagator textMapPropagator() {
        return JaegerPropagator.getInstance();
    }

    @Bean
    public SpanExporter spanExporter() {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint(oTelProperties.getSpanExporterEndpoint())
                .build();
    }

    @Bean
    @ConditionalOnClass(name = "io.opentelemetry.javaagent.OpenTelemetryAgent")
    public MeterRegistry meterRegistry() {
        Optional<MeterRegistry> openTelemetryMeterRegistry = Metrics.globalRegistry.getRegistries().stream()
                .filter(registry -> registry.getClass().getName().contains("OpenTelemetryMeterRegistry"))
                .findAny();
        openTelemetryMeterRegistry.ifPresent(Metrics.globalRegistry::remove);
        return openTelemetryMeterRegistry.orElse(null);
    }

}
