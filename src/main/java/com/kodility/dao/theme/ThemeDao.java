package com.kodility.dao.theme;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.theme.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao extends AbstractHibernateDao<Theme> {

    protected ThemeDao() {
        super(Theme.class);
    }

}
