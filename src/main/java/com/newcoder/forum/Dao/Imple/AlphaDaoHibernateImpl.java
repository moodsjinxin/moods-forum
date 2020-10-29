package com.newcoder.forum.Dao.Imple;

import com.newcoder.forum.Dao.AlphaDao;

public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}
