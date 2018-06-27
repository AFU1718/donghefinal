package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaDetail;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TeaDetailDAO extends BaseDAO<TeaDetail> {
    public TeaDetailDAO() {
        super(TeaDetail.class);
    }

    public TeaDetail findByName(String name){
        String hql = "from TeaDetail t where t.name=? ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,name);
        if (query.list() == null || query.list().size() == 0) {
            return null;
        } else {
            return (TeaDetail)query.list().get(0);
        }
    }
}