package com.kozich.finance.user_service.service.scheduling;

import com.kozich.finance.user_service.service.job.MessageSendJob;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MessageScheduling {

    private final ScheduledExecutorService executorService;
    private final MessageSendJob messageSendJob;
    private static final int CORE_SIZE = 5;
    private static final long DELAY = 20L;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    public MessageScheduling(MessageSendJob mailSendJob) {
        this.executorService = Executors.newScheduledThreadPool(CORE_SIZE);
        this.messageSendJob = mailSendJob;

        this.executorService.schedule(this.messageSendJob::start, DELAY, UNIT);
    }

}
