package donghe.donghestatistics.dao;


import donghe.donghestatistics.domain.TeaInterestedPriceMonthCut;
import org.springframework.stereotype.Repository;

@Repository
public class TeaInterestedPriceMonthCutDAO extends BaseDAO<TeaInterestedPriceMonthCut> {
    public TeaInterestedPriceMonthCutDAO() {
        super(TeaInterestedPriceMonthCut.class);
    }

}