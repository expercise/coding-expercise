package com.expercise.service.challenge;

import com.expercise.repository.challenge.ChallengeRepository;
import com.expercise.service.cache.RedisCacheService;
import com.expercise.utils.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagIndexService {

    public static final String TAGS_MAP_NAME = "index:tags:%s";

    public static final String TAGS_AND_CHALLENGES_LIST_NAME = "index:tags:%s:%s:challenges";

    public static final String TAGS_INDEX_VERSION_NAME = "index:version";

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private RedisCacheService redisCacheService;

    public Map<String, Integer> getTagAndChallengeCountsMap() {
        return redisCacheService.getMap(getCurrentTagsMapName());
    }

    public void indexTags() {
        Map<String, List<Long>> challengeTagsAndIds = prepareTagsAndChallengeIdsMap();

        // TODO: clear old indexes
        // Long oldTagsIndexVersion = redisCacheService.get(TAGS_INDEX_VERSION_NAME);

        long newTagsIndexVersion = Clock.getTime().getTime();
        String newTagsIndexName = String.format(TAGS_MAP_NAME, newTagsIndexVersion);
        for (Map.Entry<String, List<Long>> entry : challengeTagsAndIds.entrySet()) {
            redisCacheService.putToHashMap(newTagsIndexName, entry.getKey(), entry.getValue().size());
            redisCacheService.addAllToList(String.format(TAGS_AND_CHALLENGES_LIST_NAME, newTagsIndexVersion, entry.getKey()), entry.getValue());
        }
        redisCacheService.set(TAGS_INDEX_VERSION_NAME, newTagsIndexVersion);
    }

    private Map<String, List<Long>> prepareTagsAndChallengeIdsMap() {
        Map<String, List<Long>> challengeTagsAndIds = new HashMap<>();

        int page = 0;
        List<Object[]> challengeTagsAndIdsFromDB;
        do {
            challengeTagsAndIdsFromDB = challengeRepository.getChallengeWithIdAndTags(new PageRequest(page, 10));

            for (Object[] eachTagsAndIdsResult : challengeTagsAndIdsFromDB) {
                Long challengeId = (Long) eachTagsAndIdsResult[0];
                String[] tags = ((String) eachTagsAndIdsResult[1]).split(";");
                for (String eachTag : tags) {
                    if (challengeTagsAndIds.containsKey(eachTag)) {
                        challengeTagsAndIds.get(eachTag).add(challengeId);
                    } else {
                        challengeTagsAndIds.put(eachTag, new ArrayList<>(Collections.singletonList(challengeId)));
                    }
                }
            }

            page++;
        } while (!challengeTagsAndIdsFromDB.isEmpty());

        return challengeTagsAndIds;
    }

    public List<Long> getChallengeIdsForTag(String tag) {
        return redisCacheService.getList(getCurrentTagsChallengesListName(tag));
    }

    private String getCurrentTagsChallengesListName(String tag) {
        Long currentTagsIndexVersion = getCurrentIndexVersion();
        return String.format(TAGS_AND_CHALLENGES_LIST_NAME, currentTagsIndexVersion, tag);
    }

    private String getCurrentTagsMapName() {
        Long currentTagsIndexVersion = getCurrentIndexVersion();
        return String.format(TAGS_MAP_NAME, currentTagsIndexVersion);
    }

    private Long getCurrentIndexVersion() {
        return redisCacheService.get(TAGS_INDEX_VERSION_NAME);
    }

}
