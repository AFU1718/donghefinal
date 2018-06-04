package donghe.donghestatistics.dao;

import donghe.donghestatistics.domain.ParamByMonth;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class ParamByMonthDAO extends BaseDAO<ParamByMonth> {
    public ParamByMonthDAO() {
        super(ParamByMonth.class);
    }

    public ParamByMonth getByYearMonth(String yearMonth){
        String hql = "from ParamByMonth p where p.yearMonth=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,yearMonth);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (ParamByMonth)query.list().get(0);
        }
    }

}