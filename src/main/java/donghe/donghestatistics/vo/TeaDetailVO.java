package donghe.donghestatistics.vo;

import donghe.donghestatistics.domain.TeaPrice;

import java.sql.Date;
import java.util.List;

public class TeaDetailVO {

    private String name;
    private Integer goodsId;
    private String year;
    private String batch;
    private String productionTechnology;
    private String specification;
    private String netContent;
    private String referencePricePerKg;
    private String referencePrice;
    private String quality;
    private String costPerformance;
    private String collectionValue;
    private String score;
    private List<TeaPrice> teaPriceList;

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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCostPerformance() {
        return costPerformance;
    }

    public void setCostPerformance(String costPerformance) {
        this.costPerformance = costPerformance;
    }

    public String getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(String collectionValue) {
        this.collectionValue = collectionValue;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<TeaPrice> getTeaPriceList() {
        return teaPriceList;
    }

    public void setTeaPriceList(List<TeaPrice> teaPriceList) {
        this.teaPriceList = teaPriceList;
    }
}
