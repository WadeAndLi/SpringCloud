import com.wade.PageApplication;
import com.wade.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PageApplication.class)
public class PageTest {
    @Autowired
    private PageService pageService;

    @Test
    public void testPage() {
        pageService.createHtml(142L);
    }
}
