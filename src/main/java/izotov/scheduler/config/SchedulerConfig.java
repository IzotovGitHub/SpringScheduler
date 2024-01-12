package izotov.scheduler.config;

import izotov.scheduler.config.jobs.JobProperties;
import izotov.scheduler.config.jobs.JobsConfig;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {
    private final JobsConfig jobsConfig;
    
    @Bean
    public Scheduler scheduler(List<Trigger> triggers, List<JobDetail> jobDetails, SchedulerFactoryBean factory) throws SchedulerException {
        factory.setWaitForJobsToCompleteOnShutdown(true);
        Scheduler scheduler = factory.getScheduler();
        revalidateJobs(jobDetails, scheduler);
        rescheduleTriggers(triggers, scheduler);
        scheduler.start();
        return scheduler;
    }
    
    private void revalidateJobs(List<JobDetail> jobDetails, Scheduler scheduler) throws SchedulerException {
        List<JobKey> jobKeys = jobDetails.stream()
                .peek(jobDetail -> {
                    try {
                        scheduler.addJob(jobDetail, true);
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(JobDetail::getKey)
                .toList();
        for (JobProperties prop : jobsConfig.getJobProperties()) {
            for (JobKey key : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(prop.jobKey().getGroup()))) {
                if (!jobKeys.contains(key)) {
                    JobDataMap dataMap = scheduler.getJobDetail(key).getJobDataMap();
                    if (dataMap.containsKey("isDefault") && dataMap.getBooleanValue("isDefault")) {
                        scheduler.deleteJob(key);
                    }
                }
            }
        }
    }
    
    private void rescheduleTriggers(List<Trigger> triggers, Scheduler scheduler) throws SchedulerException {
        for (Trigger trigger : triggers) {
            if (!scheduler.checkExists(trigger.getKey())) {
                scheduler.scheduleJob(trigger);
            } else {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            }
        }
    }
}
