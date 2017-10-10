package com.expercise.repository.theme;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.theme.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao extends AbstractHibernateDao<Theme> {

    protected ThemeDao() {
        super(Theme.class);
    }

}
