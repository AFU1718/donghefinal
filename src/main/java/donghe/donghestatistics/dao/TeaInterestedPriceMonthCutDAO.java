package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaInterestedPriceMonthCut;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TeaInterestedPriceMonthCutDAO extends BaseDAO<TeaInterestedPriceMonthCut> {
    public TeaInterestedPriceMonthCutDAO() {
        super(TeaInterestedPriceMonthCut.class);
    }

    public void updateProp(Integer goodsId, String yearMonth, Double prop) {
        String hql = "update TeaInterestedPriceMonthCut t set t.avgPrice = ?  where t.goodsId= ? and t.yearMonth=? ";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble(0,prop);
        query.setInteger(1, goodsId);
        query.setString(2, yearMonth);

        query.executeUpdate();

    }

    public void deleteByGoodsIdAndYearMonth(Integer goodsId,String yearMonth){
        String hql="delete TeaInterestedPriceMonthCut t where t.goodsId= ? and t.yearMonth=?";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0, goodsId);
        query.setString(1, yearMonth);
        query.executeUpdate();

    }

    public Double getAvgPriceByGoodsIdAndYearMonth(Integer goodsId,String yearMonth){
        String hql = "SELECT t.avgPrice from TeaInterestedPriceMonthCut t where t.goodsId=? and t.yearMonth=? ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0,goodsId);
        query.setString(1, yearMonth);

        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Double) query.list().get(0);
        }
    }
    public Boolean existOrNotByYearMonth(String yearMonth){
        String hql = "from TeaInterestedPriceMonthCut t where t.yearMonth=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, yearMonth);
        query.setFirstResult(0);
        query.setMaxResults(1);
        if (query.list() == null || query.list().size() == 0) {
            return false;
        } else {
            return true;
        }
    }
    public List<Integer> getGoodsIdByYearMonth(String yearMonth){
        String hql = "select distinct(t.goodsId) from  TeaInterestedPriceMonthCut t where t.yearMonth=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, yearMonth);

        List list = query.list();
        List<Integer> goodsIdList =new ArrayList<>();
        for (Object o:list) {

            Integer i=(Integer)(o);
            goodsIdList.add(i);
        }
        return goodsIdList;
    }
    public List<TeaInterestedPriceMonthCut> getTeaInterestedPriceMonthCutList(){
        String hql = "from TeaInterestedPriceMonthCut t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }
    public void updateEstimatedAvgPrice(Integer goodsId, String yearMonth, Double estimatedAvgPrice) {
        String hql = "update TeaInterestedPriceMonthCut t set t.estimatedAvgPrice = ?  where t.goodsId= ? and t.yearMonth=? ";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble(0,estimatedAvgPrice);
        query.setInteger(1, goodsId);
        query.setString(2, yearMonth);
        query.executeUpdate();

    }

}