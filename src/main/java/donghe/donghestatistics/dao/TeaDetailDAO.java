package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaDetail;
import org.springframework.stereotype.Repository;

@Repository
public class TeaDetailDAO extends BaseDAO<TeaDetail> {
    public TeaDetailDAO() {
        super(TeaDetail.class);
    }

}