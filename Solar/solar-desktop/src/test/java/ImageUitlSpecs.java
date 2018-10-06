import com.itextpdf.text.DocumentException;
import com.solar.utils.ImageUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Sujan
 */
public class ImageUitlSpecs {
    @Test
    public void test(){
        System.err.println(ImageUtils.spaceBetweenEachLetter("OrientationTest"));
    }
    @Test
    public void checkImageOrientation() throws IOException, DocumentException {
        String rocketId = "OrientationTest1";
        String path = "D:\\Solar\\solar\\image\\test";
        String prefix = "HAVOC";
        File[] files = new File(path).listFiles();
        ImageUtils.toPdf(path, prefix, rocketId, rocketId, new ArrayList<>(Arrays.asList(files)));
        //ImageUtils.toPlainPdf(path, prefix, rocketId, new ArrayList<>(Arrays.asList(files)));
        
    }
}