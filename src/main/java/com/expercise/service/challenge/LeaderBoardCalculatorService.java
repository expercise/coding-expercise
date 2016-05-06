package com.expercise.service.challenge;

import com.expercise.service.cache.RedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LeaderBoardCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderBoardCalculatorService.class);

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private LeaderBoardService leaderBoardService;

    @PostConstruct
    public void init() {
        listenQueue();
    }

    public void listenQueue() {
        executorService.submit((Runnable) () -> {
            while (true) {
                try {
                    Long userId = cacheService.leftPop(UserPointService.LEADERBOARD_QUEUE);
                    if (userId != null) {
                        leaderBoardService.updateLeaderBoardPoint(userId);
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    LOGGER.error("An error occured while calculating points", e);
                }
            }

        });

    }
}
