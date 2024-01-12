package izotov.scheduler.config.jobs;

import izotov.scheduler.config.jobs.factory.YamlPropertySourceFactory;
import lombok.Getter;
import org.quartz.JobBuilder;
import org.quartz.JobKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:scheduler/jobs.yaml", factory = YamlPropertySourceFactory.class)
@Getter
public class JobsConfig {
    
    public List<JobProperties> jobProperties = new ArrayList<>();
    public List<TriggerProperties> triggerProperties = new ArrayList<>();
    
    public void setJobs(Map<String, Map<String, Object>> jobs) {
        for (Map.Entry<String, Map<String, Object>> entry : jobs.entrySet()) {
            String jobGroup = entry.getKey();
            for (Map.Entry<String, Object> jobProps : entry.getValue().entrySet()) {
                String jobName = jobProps.getKey();
                JobKey jobKey = new JobKey(jobName, jobGroup);
                Map<String, Object> props = (Map<String, Object>) jobProps.getValue();
            }
        }
    }
    
    private JobProperties buildJobProperties(JobKey jobKey, Map<String, Object> props) {
    
    }
}
