package izotov.scheduler.job;

import izotov.scheduler.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@RequiredArgsConstructor
public class CommonJob extends QuartzJobBean {
    
    private final ShowTimeService showTimeService;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        LocalDateTime lastExecutionDateTime = (LocalDateTime) dataMap.get("lastExecutionDateTime");
        dataMap.put("lastExecutionDateTime", showTimeService.showTime(lastExecutionDateTime));
    }
}
