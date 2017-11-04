package com.expercise.service.challenge;

import com.expercise.controller.challenge.model.TagModel;
import com.expercise.utils.NumberUtils;
import com.expercise.utils.TextUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TagService {

    public List<TagModel> prepareTagModels() {
        // TODO ufuk: complete with actual tags data

        List<TagModel> tags = new Random().ints(RandomUtils.nextInt(5, 30), 1, 10)
                .mapToObj(i -> new TagModel(
                        TextUtils.randomAlphabetic(RandomUtils.nextInt(5, 10)).toLowerCase(Locale.ENGLISH),
                        RandomUtils.nextInt(5, 100)
                ))
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
            BigDecimal scaledRank = NumberUtils.scaleAndTranslateIntoRange(
                    BigDecimal.valueOf(t.getCount()),
                    BigDecimal.valueOf(minCount),
                    BigDecimal.valueOf(maxCount),
                    BigDecimal.valueOf(1),
                    BigDecimal.valueOf(4)
            );
            t.setRank(scaledRank.intValue());
        });
    }

}
