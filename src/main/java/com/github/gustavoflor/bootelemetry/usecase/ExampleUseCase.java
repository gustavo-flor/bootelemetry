package com.github.gustavoflor.bootelemetry.usecase;

import com.github.gustavoflor.bootelemetry.repository.ExampleRepository;
import com.github.gustavoflor.bootelemetry.service.ExampleService;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExampleUseCase {

    private final Tracer tracer;
    private final ExampleService exampleService;
    private final ExampleRepository exampleRepository;

    public void example() {
        final var span = tracer.nextSpan()
                .name(getClass().getSimpleName());
        try (final var discarded = tracer.withSpan(span)) {
            log.info("Doing some cool use case process...");
            exampleService.example();
            span.event("Example service process finished");
            exampleRepository.example();
            span.event("Example repository process finished");
            log.info("Finished cool use case process!");
        } finally {
            span.end();
        }
    }

}
