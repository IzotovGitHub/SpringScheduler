package izotov.scheduler.config.jobs;

import org.quartz.JobKey;

import java.util.Map;


public record JobProperties(
        JobKey jobKey,
        String description,
        boolean requestRecovery,
        boolean storeDurably,
        Map<String, Object> jobData,
        String jobClass
) {
    public static final String ATTR_DESCRIPTION = "description";
    public static final String ATTR_REQUEST_RECOVERY = "requestRecovery";
    public static final String ATTR_STORE_DURABLY = "storeDurably";
    public static final String ATTR_JOB_CLASS = "jobClass";
    public static final String ATTR_JOB_DATA = "jobData";
    public static final String ATTR_TRIGGER = "trigger";
}
