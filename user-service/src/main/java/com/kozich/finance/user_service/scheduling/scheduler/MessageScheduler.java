package com.kozich.finance.user_service.scheduling.scheduler;

import com.kozich.finance.user_service.scheduling.job.MessageSendJob;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MessageScheduler {

    private final MessageSendJob messageSendJob;

    public MessageScheduler(MessageSendJob mailSendJob) {
        this.messageSendJob = mailSendJob;

    }

    @Async("taskExecutor")
    @Scheduled(fixedRateString = "${message.sender.fixedRate.in.milliseconds}")
    public void scheduleFixedRate() {
        messageSendJob.start();
    }

}
