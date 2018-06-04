package donghe.donghestatistics.dao;

import donghe.donghestatistics.domain.TeaPriceMonth;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class TeaPriceMonthDAO extends BaseDAO<TeaPriceMonth> {
    public TeaPriceMonthDAO() {
        super(TeaPriceMonth.class);
    }

    public List<TeaPriceMonth> getTeaPriceMonthByGoodsId(Integer goodsId){
        String hql="from TeaPriceMonth t where t.goodsId=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0, goodsId);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return  query.list();
        }
    }

    public Boolean existOrNotByGoodsIdAndYearMonth(Integer goodsId,String yearMonth){
        String hql = "from TeaPriceMonth t where t.goodsId=? and t.yearMonth=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,goodsId);
        query.setString(1, yearMonth);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (query.list() == null || query.list().size() == 0) {
            return false;
        } else {
            return true;
        }
    }
    public Double getAvgPriceByGoodsIdAndYearMonth(Integer goodsId,String yearMonth){
        String hql = "SELECT t.avgPrice from TeaPriceMonth t where t.goodsId=? and t.yearMonth=? ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0,goodsId);
        query.setString(1, yearMonth);

        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Double) query.list().get(0);
        }
    }
}