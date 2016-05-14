package com.expercise.controller.leaderboard;

import com.expercise.service.challenge.LeaderBoardService;
import com.expercise.service.challenge.model.LeaderBoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/leaderBoard")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @RequestMapping
    public ModelAndView leaderBoardLandingPage() {
        ModelAndView modelAndView = new ModelAndView("leaderBoard/leaderBoard");

        List<LeaderBoardModel> top10Users = leaderBoardService.getTop10UsersInLeaderBoard();

        modelAndView.addObject("top10Users", top10Users);

        return modelAndView;
    }

}
