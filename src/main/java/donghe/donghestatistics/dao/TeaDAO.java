package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.Tea;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TeaDAO extends BaseDAO<Tea> {
    public TeaDAO() {
        super(Tea.class);
    }


    public void createOrUpdate(String name, Integer goodsId) {
        if (existOrNot(name)) {
            update(name, goodsId);

        } else {
            Tea tea = new Tea();
            tea.setName(name);
            tea.setGoodsId(goodsId);
            create(tea);
        }
    }

    public void update(String name, Integer goodsId) {
        String hql = "update Tea t set t.goodsId = ?  where t.name= ?";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0, goodsId);
        query.setString(1, name);

        query.executeUpdate();
    }


    public Boolean existOrNot(String name) {
        String hql = "from Tea t where t.name=? ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, name);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (query.list() == null || query.list().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getNameByGoodsId(Integer goodsId) {
        String hql = "SELECT t.name from Tea t where t.goodsId=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0, goodsId);

        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (String) query.list().get(0);
        }
    }

    public List<Integer> getGoodsIdList() {
        String hql="SELECT distinct t.goodsId from Tea t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return  query.list();
        }
    }

    public void mark(Integer goodsId) {
        String hql = "update Tea t set t.status = 2  where t.goodsId= ?";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0, goodsId);

        query.executeUpdate();
    }
}