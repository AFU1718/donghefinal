package donghe.donghestatistics.dao;

import donghe.donghestatistics.domain.PriceMonthAvg;
import org.springframework.stereotype.Repository;


@Repository
public class PriceMonthAvgDAO extends BaseDAO<PriceMonthAvg> {
    public PriceMonthAvgDAO() {
        super(PriceMonthAvg.class);
    }
}
