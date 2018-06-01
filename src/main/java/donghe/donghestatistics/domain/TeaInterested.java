package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_tea_interested")

public class TeaInterested extends BaseEntity<TeaInterested> {
    private String name;
    private Integer goodsId;
    private Double reputation;
    private Double year;
    private Double brand;
    private Double area;
    private Double scarcity;
    private Double seasoning;
    private Double flavor;



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


    public Double getReputation() {
        return reputation;
    }

    public void setReputation(Double reputation) {
        this.reputation = reputation;
    }

    public Double getYear() {
        return year;
    }

    public void setYear(Double year) {
        this.year = year;
    }

    public Double getBrand() {
        return brand;
    }

    public void setBrand(Double brand) {
        this.brand = brand;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getScarcity() {
        return scarcity;
    }

    public void setScarcity(Double scarcity) {
        this.scarcity = scarcity;
    }

    public Double getSeasoning() {
        return seasoning;
    }

    public void setSeasoning(Double seasoning) {
        this.seasoning = seasoning;
    }

    public Double getFlavor() {
        return flavor;
    }

    public void setFlavor(Double flavor) {
        this.flavor = flavor;
    }



    public Double getByOrder(int order) {
        if (order==1){
            return 0.20;
        }
        if (order==2){
            return 0.25;
        }
        if (order==3){
            return 0.15;
        }
        if (order==4){
            return this.getReputation()*0.10;
        }
        if (order==5){
            return this.getYear()*0.03;
        }
        if (order==6){
            return this.getBrand()*0.07;
        }
        if (order==7){
            return this.getArea()*0.10;
        }
        if (order==8){
            return this.getScarcity()*0.04;
        }
        if (order==9){
            return this.getSeasoning()*0.03;
        }
        if (order==10){
            return this.getFlavor()*0.03;
        }
        if (order==11){
            return 1.0;
        }
        return 1.0;
    }
}
