package com.github.gustavoflor.bootelemetry.web.controller;

import com.github.gustavoflor.bootelemetry.usecase.ExampleUseCase;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.github.gustavoflor.bootelemetry.web.HttpHeaders.EXAMPLE_HEADER;

@Slf4j
@RestController
@RequestMapping("/v1/example")
@RequiredArgsConstructor
public class ExampleController {

    private final Tracer tracer;
    private final ExampleUseCase exampleUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void example(@RequestHeader(EXAMPLE_HEADER) String exampleHeader) {
        final var span = tracer.nextSpan()
                .name(getClass().getSimpleName());
        try (final var discarded = tracer.withSpan(span)) {
            log.info("Doing some cool controller process...");
            span.tag(EXAMPLE_HEADER, exampleHeader);
            exampleUseCase.example(exampleHeader);
            log.info("Finished cool controller process!");
        } finally {
            span.end();
        }
    }

}
