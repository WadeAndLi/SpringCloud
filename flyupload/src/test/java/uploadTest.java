import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wade.FlyUpLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlyUpLoad.class)
public class uploadTest {

    @Autowired
    private FastFileStorageClient fileStorageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws Exception{
        File file = new File("D:/flyproject/static/1.png");
        StorePath storePath = this.fileStorageClient.uploadFile(
                new FileInputStream(file), file.length(), "png", null
        );
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadThumb() throws Exception {
        File file = new File("D:/flyproject/static/1.png");
        StorePath storePath = this.fileStorageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "png", null
        );
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
    }
}
