import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import donghe.donghestatistics.service.TeaService;

public class test {
    @Autowired
     private TeaService teaService;

    @Test
    public void test() throws Exception{
        teaService.getGoodsPriceByGoodsId(1721,"1990-08-08","2020-08-08");

    }
}
