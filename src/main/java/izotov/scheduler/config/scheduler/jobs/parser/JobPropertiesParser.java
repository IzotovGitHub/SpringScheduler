package izotov.scheduler.config.scheduler.jobs.parser;

import izotov.scheduler.config.jobs.JobProperties;
import org.quartz.JobKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static izotov.scheduler.config.jobs.JobProperties.ATTR_JOB_DATA;

public class JobPropertiesParser {
    
    public static List<JobProperties> parse(String groupName, Map<String, Object> jobs) {
        return jobs.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> jobData = (Map<String, Object>) ((Map<String, Object>) entry.getValue()).getOrDefault(ATTR_JOB_DATA, new HashMap<>());
                    return new JobProperties(new JobKey(entry.getKey(), groupName), jobData);
                }).toList();
    }
    
}
