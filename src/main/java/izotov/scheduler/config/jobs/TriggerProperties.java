package izotov.scheduler.config.jobs;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

public record TriggerProperties(TriggerKey triggerKey, JobKey jobKey, String cron) {
    
    public static final String ATTR_CRON = "cron";
    public static final String ATTR_JOB_GROUPS = "forJobGroups";
    public static final String ATTR_JOB_NAMES = "forJobNames";
}
