package com.kodility.service.theme;

import com.kodility.dao.theme.ThemeDao;
import com.kodility.domain.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeDao themeDao;

    public List<Theme> getAll() {
        return themeDao.findAllOrderedByPriority();
    }

    public Theme findById(Long id) {
        return themeDao.findOne(id);
    }

}
