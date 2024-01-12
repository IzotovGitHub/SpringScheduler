package izotov.scheduler.config.scheduler.triggers.parser;

import izotov.scheduler.config.jobs.TriggerProperties;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import java.util.*;

import static izotov.scheduler.config.jobs.TriggerProperties.*;

public class TriggerPropertiesParser {
    
    public static List<TriggerProperties> parse(String groupName, Map<String, Object> triggers) {
        return triggers.entrySet().stream()
                .flatMap(entry -> {
                    String name = entry.getKey();
                    Map<String, Object> props = (Map<String, Object>) entry.getValue();
                    String cron = Optional.ofNullable((String) props.get(ATTR_CRON))
                            .filter(StringUtils::isNotBlank)
                            .orElseThrow(RuntimeException::new);
                    Collection<String> jobGroups = castToCollectionOfString(props.get(ATTR_JOB_GROUPS));
                    Map<String, Object> jobNamesByGroup = (Map<String, Object>) props.getOrDefault(ATTR_JOB_NAMES, new HashMap<>());
                    
                    List<JobKey> jobKeys = jobGroups.stream()
                            .flatMap(jobGroup -> {
                                Collection<String> jobNames = castToCollectionOfString(jobNamesByGroup.get(jobGroup));
                                return jobNames.stream()
                                        .map(jobName -> new JobKey(jobName, jobGroup));
                            }).toList();
                    
                    return jobKeys.stream()
                            .map(jobKey -> new TriggerProperties(new TriggerKey(name, groupName), jobKey, cron));
                }).toList();
    }
    
    private static Collection<String> castToCollectionOfString(Object obj) {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            return (Collection<String>) map.values();
        }
        throw new RuntimeException("");
    }
}
