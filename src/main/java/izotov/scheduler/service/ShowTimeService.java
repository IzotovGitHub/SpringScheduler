package izotov.scheduler.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ShowTimeService {
    
    public LocalDateTime showTime(LocalDateTime lastExecutionDateTime) {
        System.out.println();
        if (Objects.nonNull(lastExecutionDateTime)) {
            System.out.printf("Previous execution time: %s", lastExecutionDateTime);
        } else {
            System.out.println("No previous executions found");
        }
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("Current execution time: %s", now);
        return now;
    }
}
