package com.expercise.service.challenge;

import com.expercise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Component
public class LeaderBoardWarmUpUtil {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void init() {
        warmUpLeaderBoard();
    }

    private void warmUpLeaderBoard() {
        new Thread(() -> {
            new TransactionTemplate(transactionManager).execute(status -> {
                userService.findAllIds()
                        .forEach(id -> leaderBoardService.updateLeaderBoardPoint(id));

                return null;
            });
        }).start();
    }

}
