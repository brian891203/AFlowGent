package io.csd.cloudtechnology.aflowgent;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
// @RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApplicationStartup {

    private String key = "";

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() throws Exception {

    }

}
