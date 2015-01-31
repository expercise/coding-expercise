package com.expercise.dao.theme;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.theme.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao extends AbstractHibernateDao<Theme> {

    protected ThemeDao() {
        super(Theme.class);
    }

}
