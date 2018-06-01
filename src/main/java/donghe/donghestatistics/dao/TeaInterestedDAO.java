package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaInterested;
import org.springframework.stereotype.Repository;

@Repository
public class TeaInterestedDAO extends BaseDAO<TeaInterested> {
    public TeaInterestedDAO() {
        super(TeaInterested.class);
    }



}
