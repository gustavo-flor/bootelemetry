package com.github.gustavoflor.bootelemetry.service;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

    private final Tracer tracer;

    public void example() {
        final var span = tracer.nextSpan()
                .name(getClass().getSimpleName());
        try (final var discarded = tracer.withSpan(span)) {
            log.info("Some cool service done.");
        } finally {
            span.end();
        }
    }

}
