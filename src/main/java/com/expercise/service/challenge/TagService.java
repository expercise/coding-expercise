package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.TagModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagIndexService tagIndexService;

    @Autowired
    private ChallengeService challengeService;

    public List<TagModel> prepareTagModels() {
        List<TagModel> tags = tagIndexService.getTagAndChallengeCountsMap()
                .entrySet()
                .stream()
                .map(e -> new TagModel(e.getKey(), e.getValue().intValue()))
                .sorted(Comparator.comparing(TagModel::getName))
                .collect(Collectors.toList());

        if (tags.size() > 1) {
            scaleTagRanks(tags);
        }
        return tags;
    }

    private void scaleTagRanks(List<TagModel> tags) {
        List<Integer> sortedCounts = tags.stream()
                .map(TagModel::getCount)
                .sorted()
                .collect(Collectors.toList());

        Integer minCount = sortedCounts.get(0);
        Integer maxCount = sortedCounts.get(sortedCounts.size() - 1);

        tags.forEach(t -> {
            BigDecimal desiredMax = BigDecimal.valueOf(4);
            BigDecimal scaledRank =
                    maxCount - minCount > 0
                            ? NumberUtils.scaleAndTranslateIntoRange(BigDecimal.valueOf(t.getCount()), BigDecimal.valueOf(minCount), BigDecimal.valueOf(maxCount), BigDecimal.valueOf(1), desiredMax)
                            : desiredMax;
            t.setRank(scaledRank.intValue());
        });
    }

    public List<Challenge> getChallengesForTags(String tags) {
        List<Long> challengeIds = tagIndexService.getChallengeIdsForTag(tags);
        return challengeIds.stream()
                .map(id -> challengeService.findById(id))
                .collect(Collectors.toList());
    }

}
