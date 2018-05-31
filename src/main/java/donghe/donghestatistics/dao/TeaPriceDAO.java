package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaPrice;
import donghe.donghestatistics.domain.TeaPriceMonth;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

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
            teaPriceMonth.setGoodsId((Integer)((Object[])(o))[1]);
            teaPriceMonth.setYearMonth(String.valueOf(((Object[])(o))[0]));
            teaPriceMonth.setAvgPrice(Double.parseDouble(((Object[])(o))[0].toString()));
            teaPriceMonthList.add(teaPriceMonth);
        }
        return teaPriceMonthList;
    }
}