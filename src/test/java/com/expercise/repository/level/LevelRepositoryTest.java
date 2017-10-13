package com.expercise.repository.level;

import com.expercise.BaseSpringIntegrationTest;
import com.expercise.domain.level.Level;
import com.expercise.testutils.builder.LevelBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertOrderedItems;

public class LevelRepositoryTest extends BaseSpringIntegrationTest {

    @Autowired
    private LevelRepository levelRepository;

    @Test
    public void shouldFindAllOrdered() {
        Level level1 = new LevelBuilder().priority(3).persist(getEntityManager());
        Level level2 = new LevelBuilder().priority(1).persist(getEntityManager());
        Level level3 = new LevelBuilder().priority(2).persist(getEntityManager());

        flushAndClear();

        List<Level> orderedLevels = levelRepository.findAllByOrderByPriority();

        assertOrderedItems(orderedLevels, level2, level3, level1);
    }

}