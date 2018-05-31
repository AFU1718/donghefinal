package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.BaseEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lin on 2017/12/10.
 */
public class BaseDAO<T extends BaseEntity> {

    @Autowired
    protected SessionFactory sessionFactory;

    private Class<T> type;

    public BaseDAO(Class<T> type) {
        this.type = type;
    }

    public T get(Long id) {
        return sessionFactory.getCurrentSession().get(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return (List<T>) sessionFactory.getCurrentSession().createCriteria(type).list();
    }

    public void create(T entity) { sessionFactory.getCurrentSession().saveOrUpdate(entity); }

    public void update(T entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public Long count(){
        String hql = "select count(*) FROM " + type.getSimpleName();
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        Long count = Long.parseLong(query.uniqueResult().toString());
        return count;
    }

    public Long count(String where){
        String hql = "select count(*) FROM " + type.getSimpleName() + " WHERE " + where;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        Long count = Long.parseLong(query.uniqueResult().toString());
        return count;
    }
//    public WxUser getWxUser(String sid) {
//        String hql = "FROM WxSession WHERE sid = '" + sid + "'";
//        Enquiry query = sessionFactory.getCurrentSession().createQuery(hql);
//        query.setMaxResults(1);
//        List<WxSession> list = query.list();
//        if (list.size() < 1) {
//            return null;
//        }
//        String hql1 = "FROM WxUser WHERE openid = '" + list.get(0).getOpenId() + "'";
//        Enquiry query1 = sessionFactory.getCurrentSession().createQuery(hql1);
//        List<WxUser> list1 = query1.list();
//        if (list1.size() < 1) {
//            return null;
//        }
//        return list1.get(0);
//
//    }




}
