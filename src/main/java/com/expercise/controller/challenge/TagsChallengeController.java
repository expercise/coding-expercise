package com.expercise.controller.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.challenge.SolutionService;
import com.expercise.service.challenge.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/challenges")
public class TagsChallengeController {

    @Autowired
    private TagService tagService;

    @Autowired
    private SolutionCountService solutionCountService;

    @Autowired
    private SolutionService solutionService;

    @RequestMapping("/tags/{tags}")
    public ModelAndView listChallengesForTags(@PathVariable String tags) {
        ModelAndView modelAndView = new ModelAndView("tag/tagsChallengeList");
        modelAndView.addObject("tags", tags);

        List<Challenge> challengeList = tagService.getChallengesForTags(tags);
        modelAndView.addObject("challengeList", challengeList);
        modelAndView.addObject("solutionCountMap", solutionCountService.prepareSolutionCountMapFor(challengeList));
        modelAndView.addObject("solvedChallengesByCurrentUser", solutionService.getSolvedChallengesOfCurrentUser());

        return modelAndView;
    }

}
