package employee.service.management.command.controller;

import employee.service.management.core.base_controller.BaseController;
import employee.service.management.core.domain.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventsReplayController extends BaseController {
    private final EventProcessingConfiguration eventProcessingConfiguration;

    @PostMapping("/eventProcessor/{processorName}")
    public ResponseEntity<?> replayEvents(@PathVariable String processorName) {
        {
            Optional<TrackingEventProcessor> trackingEventProcessor = eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);
            if (trackingEventProcessor.isPresent()) {
                TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
                eventProcessor.shutDown();
                eventProcessor.resetTokens();
                eventProcessor.start();
                return new ResponseEntity<>(new ResponseDto<>("replaying started"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDto<>("only tracking event processor is supported"), HttpStatus.OK);
            }
        }
    }
}
