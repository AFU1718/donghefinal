package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaInterestedPriceMonthCut;
import org.springframework.stereotype.Repository;

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
}