package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "t_tea_interested_price_month_cut")
public class TeaInterestedPriceMonthCut extends BaseEntity<donghe.donghestatistics.domain.TeaInterestedPriceMonthCut> {
    private String name;
    private Integer goodsId;
    private Double avgPrice;
    private String yearMonth;
    private Double estimatedAvgPrice;

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

    public Double getEstimatedAvgPrice() {
        return estimatedAvgPrice;
    }

    public void setEstimatedAvgPrice(Double estimatedAvgPrice) {
        this.estimatedAvgPrice = estimatedAvgPrice;
    }
}