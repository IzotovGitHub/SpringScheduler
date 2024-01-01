package izotov.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerProperties {
    
    String groupName;
    
    String cron;
}
