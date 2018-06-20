package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "t_price_month_avg")
public class PriceMonthAvg extends BaseEntity<PriceMonthAvg> {
    private String yearMonth;
    private Double priceMonthAvg;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getPriceMonthAvg() {
        return priceMonthAvg;
    }

    public void setPriceMonthAvg(Double priceMonthAvg) {
        this.priceMonthAvg = priceMonthAvg;
    }
}
