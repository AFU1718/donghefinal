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


    public List<Integer> getGoodsIdInterested(){
        String hql="SELECT distinct t.goodsId from TeaInterested t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return  query.list();
        }
    }


}
