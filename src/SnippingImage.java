import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 对图片进行截图 保存 （验证用）
 * @author mac_py
 *
 */
public class SnippingImage {
	public static void saveImageWithSize(int x,int y,int w,int h,String src,String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);

        try {
			BufferedImage bufImg = ImageIO.read(srcFile);
			BufferedImage subimage = bufImg.getSubimage(x, y, w, h);
			ImageIO.write((BufferedImage) subimage,dest.substring(dest.lastIndexOf(".")+1), destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
