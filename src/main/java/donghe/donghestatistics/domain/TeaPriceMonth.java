package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name = "t_tea_price_month")
public class TeaPriceMonth extends BaseEntity<donghe.donghestatistics.domain.TeaPriceMonth> {
    private String name;
    private Integer goodsId;
    private Double avgPrice;
    private String yearMonth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
}