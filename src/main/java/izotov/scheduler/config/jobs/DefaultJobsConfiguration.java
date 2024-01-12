package izotov.scheduler.config.jobs;

import izotov.scheduler.config.scheduler.triggers.TriggersConfig;
import izotov.scheduler.job.CommonJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultJobsConfiguration {
    private final JobsConfig jobsConfig;
    private final TriggersConfig triggersConfig;
    
    @Bean
    public List<JobDetail> jobDetails() {
        return jobsConfig.getJobProperties().stream()
                .map(jobProperties ->
                        JobBuilder.newJob(CommonJob.class)
                                .withIdentity(jobProperties.jobKey())
                                .usingJobData(new JobDataMap(jobProperties.jobData()))
                                .storeDurably()
                                .requestRecovery(true)
                                .build()
                ).toList();
    }
    
    @Bean
    public List<Trigger> triggers() {
        List<Trigger> triggers = new ArrayList<>();
        for (TriggerProperties properties : triggersConfig.getTriggerProperties()) {
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(properties.jobKey())
                    .withIdentity(properties.triggerKey())
                    .withSchedule(CronScheduleBuilder.cronSchedule(properties.cron()))
                    .build();
            triggers.add(trigger);
        }
        return triggers;
    }
}
