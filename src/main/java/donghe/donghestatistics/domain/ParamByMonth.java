package donghe.donghestatistics.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_param_by_month")

public class ParamByMonth extends BaseEntity<ParamByMonth> {

    private String yearMonth;
    private Double outerParam;
    private Double reputationParam;
    private Double yearParam;

    private Double scarcityParam;

    private Double brand_areaParam;

    private Double seasoning_flavorParam;

    private Double intercept;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getOuterParam() {
        return outerParam;
    }

    public void setOuterParam(Double outerParam) {
        this.outerParam = outerParam;
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

    public Double getScarcityParam() {
        return scarcityParam;
    }

    public void setScarcityParam(Double scarcityParam) {
        this.scarcityParam = scarcityParam;
    }

    public Double getBrand_areaParam() {
        return brand_areaParam;
    }

    public void setBrand_areaParam(Double brand_areaParam) {
        this.brand_areaParam = brand_areaParam;
    }

    public Double getSeasoning_flavorParam() {
        return seasoning_flavorParam;
    }

    public void setSeasoning_flavorParam(Double seasoning_flavorParam) {
        this.seasoning_flavorParam = seasoning_flavorParam;
    }

    public Double getIntercept() {
        return intercept;
    }

    public void setIntercept(Double intercept) {
        this.intercept = intercept;
    }
}
