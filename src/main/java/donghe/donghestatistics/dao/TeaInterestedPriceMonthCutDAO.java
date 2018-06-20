package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.PriceMonthAvg;
import donghe.donghestatistics.domain.TeaInterestedPriceMonthCut;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeaInterestedPriceMonthCutDAO extends BaseDAO<TeaInterestedPriceMonthCut> {
    public TeaInterestedPriceMonthCutDAO() {
        super(TeaInterestedPriceMonthCut.class);
    }

    public void updateProp(Integer goodsId, String yearMonth, Double prop) {
        String hql = "update TeaInterestedPriceMonthCut t set t.avgPrice = ?  where t.goodsId= ? and t.yearMonth=? ";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble(0, prop);
        query.setInteger(1, goodsId);
        query.setString(2, yearMonth);

        query.executeUpdate();

    }

    public void deleteByGoodsIdAndYearMonth(Integer goodsId, String yearMonth) {
        String hql = "delete TeaInterestedPriceMonthCut t where t.goodsId= ? and t.yearMonth=?";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0, goodsId);
        query.setString(1, yearMonth);
        query.executeUpdate();

    }

    public Double getAvgPriceByGoodsIdAndYearMonth(Integer goodsId, String yearMonth) {
        String hql = "SELECT t.avgPrice from TeaInterestedPriceMonthCut t where t.goodsId=? and t.yearMonth=? ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);

        query.setInteger(0, goodsId);
        query.setString(1, yearMonth);

        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (Double) query.list().get(0);
        }
    }

    public Boolean existOrNotByYearMonth(String yearMonth) {
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

    public List<Integer> getGoodsIdByYearMonth(String yearMonth) {
        String hql = "select distinct(t.goodsId) from  TeaInterestedPriceMonthCut t where t.yearMonth=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, yearMonth);

        List list = query.list();
        List<Integer> goodsIdList = new ArrayList<>();
        for (Object o : list) {

            Integer i = (Integer) (o);
            goodsIdList.add(i);
        }
        return goodsIdList;
    }

    public List<TeaInterestedPriceMonthCut> getTeaInterestedPriceMonthCutList() {
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
        query.setDouble(0, estimatedAvgPrice);
        query.setInteger(1, goodsId);
        query.setString(2, yearMonth);
        query.executeUpdate();

    }

    public List<PriceMonthAvg> getAverage() {
        String hql = "select t.yearMonth,avg(t.avgPrice) from TeaInterestedPriceMonthCut t GROUP BY t.yearMonth";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List list = query.list();

        List<PriceMonthAvg> priceMonthAvgList=new ArrayList<>();
        for (Object o : list) {
            String yearMonth = String.valueOf(((Object[]) (o))[0]);
            Double avg = Double.parseDouble(((Object[]) (o))[1].toString());
            PriceMonthAvg priceMonthAvg=new PriceMonthAvg();
            priceMonthAvg.setYearMonth(yearMonth);
            priceMonthAvg.setPriceMonthAvg(avg);
            priceMonthAvgList.add(priceMonthAvg);
        }
        return priceMonthAvgList;

    }
    public Double getError(){
        String hql=" select (t.avgPrice-t.estimatedAvgPrice) from  TeaInterestedPriceMonthCut t";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List list = query.list();
       Double err=0.0;
        for (Object o : list) {
            err=err+Math.pow((Double)(list.get(0)),2);
        }

      return err;

    }

}