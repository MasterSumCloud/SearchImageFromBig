import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * 根据图片搜索大图中的位置
 * 
 * @author mac_py
 *
 */
public class SearchPixelPosition {

	private int targetX;
	private int targetY;
	private int targetWidth;
	private int targetHeight;

	public ResultBean getAllRGB(String path, String tagert) {
		// int[] rgb = new int[3];
		File file = new File(path);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		System.out.println("width=" + width + ",height=" + height + ".");
		System.out.println("minx=" + minx + ",miniy=" + miny + ".");
		ArrayList<PositionBean> setTarget5RGB = setTarget5RGB(tagert);

		// System.out.println(setTarget5RGB.get(0).x+" "+setTarget5RGB.get(0).y+"
		// "+setTarget5RGB.get(0).pxrgb);
		// System.out.println(setTarget5RGB.get(1).x+" "+setTarget5RGB.get(1).y+"
		// "+setTarget5RGB.get(1).pxrgb);
		// System.out.println(setTarget5RGB.get(2).x+" "+setTarget5RGB.get(2).y+"
		// "+setTarget5RGB.get(2).pxrgb);
		// System.out.println(setTarget5RGB.get(3).x+" "+setTarget5RGB.get(3).y+"
		// "+setTarget5RGB.get(3).pxrgb);
		// System.out.println(setTarget5RGB.get(4).x+" "+setTarget5RGB.get(4).y+"
		// "+setTarget5RGB.get(4).pxrgb);

		long start = System.currentTimeMillis();
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				// rgb[0] = (pixel & 0xff0000) >> 16;
				// rgb[1] = (pixel & 0xff00) >> 8;
				// rgb[2] = (pixel & 0xff);
				if (setTarget5RGB != null) {
					PositionBean p1 = setTarget5RGB.get(0);
					if (pixel == p1.pxrgb) {
						int other = 0;
						PositionBean p2 = setTarget5RGB.get(1);
						int pixel2 = bi.getRGB(i + (p2.x - p1.x), j);
						if (pixel2 == p2.pxrgb) {
							other++;
							PositionBean p3 = setTarget5RGB.get(2);
							int pixel3 = bi.getRGB(i + (p3.x - p1.x), j + (p3.y - p1.y));
							if (pixel3 == p3.pxrgb) {
								other++;
								PositionBean p4 = setTarget5RGB.get(3);
								int pixel4 = bi.getRGB(i, j + (p4.y - p1.y));
								if (pixel4 == p4.pxrgb) {
									other++;
									PositionBean p5 = setTarget5RGB.get(4);
									int pixel5 = bi.getRGB(i + (p5.x - p1.x), j + (p5.y - p1.y));
									if (pixel5 == p5.pxrgb) {
										other++;
									}
								}
							}
						}
						if (other == 4) {
							long end = System.currentTimeMillis();
							System.out.println("总耗时：" + (end - start));
							System.out.println("找到了===》》》》横坐标" + i + "纵坐标" + j);
							ResultBean resultBean = new ResultBean();
							resultBean.width = targetWidth;
							resultBean.height = targetHeight;
							resultBean.x = i - p1.x;
							resultBean.y = j - p1.y;
							return resultBean;
						}
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("总耗时：" + (end - start));
		return null;
	}

	/**
	 * 分别取小图的四个角落和中心点的像素，作为搜图依据
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	private ArrayList<PositionBean> get5PointForTager(String src) throws Exception {
		ArrayList<PositionBean> searchXYList = new ArrayList<>();
		File file = new File(src);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		targetWidth = width;
		targetHeight = height;

		if (width >= 10 && height >= 10) {
			int px1 = (int) (width * 0.25);
			int py1 = (int) (height * 0.25);
			int px2 = (int) (width * 0.75);
			int py2 = (int) (height * 0.25);
			int px3 = (int) (width * 0.5);
			int py3 = (int) (height * 0.5);
			int px4 = (int) (width * 0.25);
			int py4 = (int) (height * 0.75);
			int px5 = (int) (width * 0.75);
			int py5 = (int) (height * 0.75);
			searchXYList.add(new PositionBean(px1, py1));
			searchXYList.add(new PositionBean(px2, py2));
			searchXYList.add(new PositionBean(px3, py3));
			searchXYList.add(new PositionBean(px4, py4));
			searchXYList.add(new PositionBean(px5, py5));
		} else {
			throw new Exception("不支持10px以内的搜索");
		}

		return searchXYList;
	}

	private ArrayList<PositionBean> setTarget5RGB(String src) {
		File file = new File(src);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ArrayList<PositionBean> get5PointForTager = get5PointForTager(src);
			for (int i = 0; i < get5PointForTager.size(); i++) {
				PositionBean positionBean = get5PointForTager.get(i);
				positionBean.pxrgb = bi.getRGB(positionBean.x, positionBean.y);
			}
			return get5PointForTager;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
