import java.io.File;
import java.io.FileInputStream;

public class MainInTest {
	public static void main(String[] args) {
		String src = "/Users/mac_py/Desktop/cocl.png";
		String dest = "/Users/mac_py/Desktop/cocl-n-y.png";
		String target = "/Users/mac_py/Desktop/cocl-n.png";
		try {
			// ScalImage.zoomImage(src, dest,320,180);
			// SnippingImage.saveImageWithSize(568,850,240,106,src,"/Users/mac_py/Desktop/cocl-n.png");
			// SnippingImage.saveImageWithSize(71,106,30,13,src,"/Users/mac_py/Desktop/cocl-n-s-y.png");
			// ScalImage.zoomImage(src, dest,30,13);
			// SearchPixelPosition.getAllRGB(src);
			SearchPixelPosition searchPixelPosition = new SearchPixelPosition();
			ResultBean result = searchPixelPosition.getAllRGB(src, target);
			if (result != null) {
				SnippingImage.saveImageWithSize(result.x, result.y, result.width, result.height, src,
						"/Users/mac_py/Desktop/cocl-ai.png");
				ImagePHash p = new ImagePHash();
				System.out.println("进行相似度计算");
				String image1 = p.getHash(new FileInputStream(new File(target)));
				String image2 = p.getHash(new FileInputStream(new File("/Users/mac_py/Desktop/cocl-ai.png")));
				System.out.println("相似度为" + p.distance(image1, image2));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
