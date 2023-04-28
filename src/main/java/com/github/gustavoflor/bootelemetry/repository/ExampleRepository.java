package com.github.gustavoflor.bootelemetry.repository;

import com.github.javafaker.Faker;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ExampleRepository {

    private final Tracer tracer;
    private final Faker faker = new Faker();

    public void example() {
        final var span = tracer.nextSpan()
                .name(getClass().getSimpleName());
        try (final var discarded = tracer.withSpan(span)) {
            log.info("Some cool repository done.");
            if (faker.bool().bool()) {
                span.error(new RuntimeException());
            }
        } finally {
            span.end();
        }
    }

}
