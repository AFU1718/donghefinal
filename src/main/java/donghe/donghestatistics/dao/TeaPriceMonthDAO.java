package donghe.donghestatistics.dao;

import donghe.donghestatistics.domain.TeaPriceMonth;
import org.springframework.stereotype.Repository;

@Repository
public class TeaPriceMonthDAO extends BaseDAO<TeaPriceMonth> {
    public TeaPriceMonthDAO() {
        super(TeaPriceMonth.class);
    }
}