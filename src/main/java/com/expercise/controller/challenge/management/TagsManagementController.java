package com.expercise.controller.challenge.management;

import com.expercise.controller.BaseManagementController;
import com.expercise.service.challenge.TagIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagsManagementController extends BaseManagementController {

    @Autowired
    private TagIndexService tagIndexService;

    @GetMapping("/tags/re-index")
    public void listChallengesForAdmin() {
        tagIndexService.indexTags();
    }

}
