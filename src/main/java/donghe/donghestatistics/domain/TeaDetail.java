package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Year;

@Entity
@Table(name = "t_tea_detail")
public class TeaDetail extends BaseEntity<TeaDetail> {
    private String name;
    private Integer goodsId;
    private String year;
    private String batch;
    private String productionTechnology;
    private String specification;
    private String netContent;
    private String referencePricePerKg;
    private String referencePrice;
    private Integer quality;
    private Integer costPerformance;
    private Integer collectionValue;
    private Integer score;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getProductionTechnology() {
        return productionTechnology;
    }

    public void setProductionTechnology(String productionTechnology) {
        this.productionTechnology = productionTechnology;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getNetContent() {
        return netContent;
    }

    public void setNetContent(String netContent) {
        this.netContent = netContent;
    }

    public String getReferencePricePerKg() {
        return referencePricePerKg;
    }

    public void setReferencePricePerKg(String referencePricePerKg) {
        this.referencePricePerKg = referencePricePerKg;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getCostPerformance() {
        return costPerformance;
    }

    public void setCostPerformance(Integer costPerformance) {
        this.costPerformance = costPerformance;
    }

    public Integer getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(Integer collectionValue) {
        this.collectionValue = collectionValue;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}