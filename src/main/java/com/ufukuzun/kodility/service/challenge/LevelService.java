package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.LevelDao;
import com.ufukuzun.kodility.domain.challenge.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelDao levelDao;

    public List<Level> getAllLevels() {
        return levelDao.findAllOrdered();
    }

    public Level findById(Long id) {
        return levelDao.findOne(id);
    }

}
