package izotov.scheduler.config;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

@Configuration
public class SchedulerConfig {
    
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
                .map(JobDetail::getKey)
                .toList();
        
        for (JobKey key : scheduler.getJobKeys(GroupMatcher.jobGroupEquals("PERMANENT"))) {
            if (!jobKeys.contains(key)) {
                scheduler.deleteJob(key);
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
