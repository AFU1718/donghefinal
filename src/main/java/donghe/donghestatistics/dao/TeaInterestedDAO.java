package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaInterested;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeaInterestedDAO extends BaseDAO<TeaInterested> {
    public TeaInterestedDAO() {
        super(TeaInterested.class);
    }


    public List<Integer> getGoodsIdInterested() {
        String hql = "SELECT distinct t.goodsId from TeaInterested t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }

    public List<TeaInterested> getTeaInterestedList() {
        String hql = "from TeaInterested t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }
    public TeaInterested getByGoodsId(Integer goodsId){
        String hql = "from TeaInterested t where t.goodsId=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,goodsId);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (TeaInterested)query.list().get(0);
        }
    }
    public void merge(){
        String hql = "update TeaInterested t set t.brand_area=(t.brand+t.area)/2,t.seasoning_flavor= (t.seasoning+t.flavor)/2";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

}
