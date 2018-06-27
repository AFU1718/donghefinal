package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaPrice;
import donghe.donghestatistics.domain.TeaPriceMonth;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeaPriceDAO extends BaseDAO<TeaPrice> {
    public TeaPriceDAO() {
        super(TeaPrice.class);
    }

    public List<TeaPriceMonth> getTeaPriceMonthByGoodsId(Integer goodsId) {
        String sql = "select tmp.name,tmp.goodsId, tmp.yearMonth,avg(tmp.price) as avgPrice from (select tp.name,tp.goodsId,date_format(tp.date,'%Y-%m') as yearMonth,tp.price from t_tea_price tp where tp.goodsId= " + goodsId.toString() + " ) as tmp group by tmp.yearMonth";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List list = query.list();
        List<TeaPriceMonth> teaPriceMonthList =new ArrayList<>();
        for (Object o:list) {
            TeaPriceMonth teaPriceMonth=new TeaPriceMonth();
            teaPriceMonth.setName(((Object[])(o))[0].toString());
            teaPriceMonth.setGoodsId((Integer)((Object[])(o))[1]);
            teaPriceMonth.setYearMonth(String.valueOf(((Object[])(o))[2]));
            teaPriceMonth.setAvgPrice(Double.parseDouble(((Object[])(o))[3].toString()));
            teaPriceMonthList.add(teaPriceMonth);
        }
        return teaPriceMonthList;
    }
    public void clearZeroPrice(){
        String hql = "delete TeaPrice t where t.price= ? ";
        org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setDouble(0, 0.0);
        query.executeUpdate();
    }

   public Boolean existOrNotByGoodsIdAndDate(Integer goodsId,Date date){
       String hql = "from TeaPrice t where t.goodsId=? and t.date=?";
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
       query.setInteger(0, goodsId);
       query.setDate(1,date);
       query.setFirstResult(0);
       query.setMaxResults(1);
       if (query.list() == null || query.list().size() == 0) {
           return false;
       } else {
           return true;
       }

    }
    public TeaPrice findByGoodsIdAndDate(Integer goodsId,Date date){
        String hql = "from TeaPrice t where t.goodsId=? and t.date=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,goodsId);
        query.setDate(1,date);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (TeaPrice)query.list().get(0);
        }

    }
    public List<TeaPrice> getTeaPriceListByGoodsId(Integer goodsId){
        String hql = "from TeaPrice t where t.goodsId=? order by t.date";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger(0,goodsId);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }
    public List<TeaPrice> findByNameAndDateInterval(String name,String beginAt,String endAt) throws Exception{

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate=new Date(sdf.parse(beginAt).getTime());
        Date endDate=new Date(sdf.parse(endAt).getTime());
        String hql = "from TeaPrice t where t.name=:name and t.date >=:beginDate and t.date <=:endDate order by t.date";

        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name",name);
       query.setDate("beginDate",beginDate);
        query.setDate("endDate",endDate);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return query.list();
        }
    }
}