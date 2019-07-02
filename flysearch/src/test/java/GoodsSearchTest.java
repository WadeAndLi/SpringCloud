import com.wade.ESApplication;
import com.wade.client.GoodsClient;
import com.wade.common.PageResult;
import com.wade.po.SearchPO;
import com.wade.po.SpuPO;
import com.wade.repository.GoodsRepository;
import com.wade.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class GoodsSearchTest {

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testCreateIndex() {
        template.createIndex(SearchPO.class);
        template.putMapping(SearchPO.class);
    }

    @Test
    public void testInsertData() {
        int page = 1;
        int size;
        do {
            PageResult<SpuPO> result = goodsClient.getSpu(page, null, 50, true);
            List<SpuPO> spus = result.getItems();
            List<SearchPO> searchPOS = spus.stream().map(searchService::buildGoods).collect(Collectors.toList());
            repository.saveAll(searchPOS);
            page ++;
            size = spus.size();
        } while (size == 50);
    }
}
