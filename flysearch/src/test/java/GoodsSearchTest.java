import com.wade.ESApplication;
import com.wade.po.SearchPO;
import com.wade.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class GoodsSearchTest {

    @Autowired
    private GoodsRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testCreateIndex() {
        template.createIndex(SearchPO.class);
        template.putMapping(SearchPO.class);
    }

    @Test
    public void testInsertData() {

    }
}
