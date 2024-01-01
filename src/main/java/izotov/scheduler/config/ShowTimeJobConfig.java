package izotov.scheduler.config;

import izotov.scheduler.job.ShowTimeJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShowTimeJobConfig {
    
    @Bean
    public JobDetail showTimeJobDetail() {
        return JobBuilder.newJob(ShowTimeJob.class)
                .withIdentity("showTimeJob", "PERMANENT")
                .storeDurably()
                .requestRecovery(true)
                .build();
    }
    
    @Bean
    public Trigger showTimeTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(showTimeJobDetail())
                .withIdentity("showTimeJobTrigger", "PERMANENT")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * * * ?"))
                .build();
    }
}
