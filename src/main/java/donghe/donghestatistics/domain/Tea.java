package donghe.donghestatistics.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "t_tea")
public class Tea extends BaseEntity<Tea> {

    private String name;
    private Integer goodsId;

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
}