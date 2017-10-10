package com.expercise.repository.level;

import com.expercise.BaseRepositoryTest;
import com.expercise.domain.level.Level;
import com.expercise.testutils.builder.LevelBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.expercise.testutils.asserts.Asserts.assertOrderedItems;

public class LevelDaoTest extends BaseRepositoryTest {

    @Autowired
    private LevelDao levelDao;

    @Test
    public void shouldFindAllOrdered() {
        Level level1 = new LevelBuilder().priority(3).persist(getCurrentSession());
        Level level2 = new LevelBuilder().priority(1).persist(getCurrentSession());
        Level level3 = new LevelBuilder().priority(2).persist(getCurrentSession());

        flushAndClear();

        List<Level> orderedLevels = levelDao.findAllOrderedByPriority();

        assertOrderedItems(orderedLevels, level2, level3, level1);
    }

}