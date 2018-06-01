package donghe.donghestatistics.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_param_by_month")

public class ParamByMonth extends BaseEntity<ParamByMonth> {

    private String yearMonth;
    private Double policyParam;
    private Double hotMoneyParam;
    private Double hypeParam;
    private Double reputationParam;
    private Double yearParam;
    private Double brandParam;
    private Double areaParam;
    private Double scarcityParam;
    private Double seasoningParam;
    private Double flavorParam;
    private Double intercept;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getPolicyParam() {
        return policyParam;
    }

    public void setPolicyParam(Double policyParam) {
        this.policyParam = policyParam;
    }

    public Double getHotMoneyParam() {
        return hotMoneyParam;
    }

    public void setHotMoneyParam(Double hotMoneyParam) {
        this.hotMoneyParam = hotMoneyParam;
    }

    public Double getHypeParam() {
        return hypeParam;
    }

    public void setHypeParam(Double hypeParam) {
        this.hypeParam = hypeParam;
    }

    public Double getReputationParam() {
        return reputationParam;
    }

    public void setReputationParam(Double reputationParam) {
        this.reputationParam = reputationParam;
    }

    public Double getYearParam() {
        return yearParam;
    }

    public void setYearParam(Double yearParam) {
        this.yearParam = yearParam;
    }

    public Double getBrandParam() {
        return brandParam;
    }

    public void setBrandParam(Double brandParam) {
        this.brandParam = brandParam;
    }

    public Double getAreaParam() {
        return areaParam;
    }

    public void setAreaParam(Double areaParam) {
        this.areaParam = areaParam;
    }

    public Double getScarcityParam() {
        return scarcityParam;
    }

    public void setScarcityParam(Double scarcityParam) {
        this.scarcityParam = scarcityParam;
    }

    public Double getSeasoningParam() {
        return seasoningParam;
    }

    public void setSeasoningParam(Double seasoningParam) {
        this.seasoningParam = seasoningParam;
    }

    public Double getFlavorParam() {
        return flavorParam;
    }

    public void setFlavorParam(Double flavorParam) {
        this.flavorParam = flavorParam;
    }

    public Double getIntercept() {
        return intercept;
    }

    public void setIntercept(Double intercept) {
        this.intercept = intercept;
    }
}
