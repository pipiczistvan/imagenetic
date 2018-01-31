package imagenetic.image;

import piengine.core.base.resource.ResourceLoader;

public class ImageProcessorTest {

    private ResourceLoader imageLoader = new ResourceLoader("images", "png");

//    @Test
//    public void convertToGrayScale() throws Exception {
//        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
//        String grayScaleFlag = getClass().getResource("/images/grayScaleFlag.png").getFile();
//
//        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
//                .toGrayScale()
//                .get();
//
//        BufferedImage expectedImage = ImageProcessor.loadImage(grayScaleFlag)
//                .get();
//
//        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
//    }
//
//    @Test
//    public void convertToNegative() throws Exception {
//        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
//        String negativeFlag = getClass().getResource("/images/negativeFlag.png").getFile();
//
//        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
//                .toNegative()
//                .get();
//
//        BufferedImage expectedImage = ImageProcessor.loadImage(negativeFlag)
//                .get();
//
//        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
//    }
//
//    @Test
//    public void resize() throws Exception {
//        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
//        String scaledFlag = getClass().getResource("/images/scaledFlag.png").getFile();
//
//        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
//                .resize(100, 200)
//                .get();
//
//        BufferedImage expectedImage = ImageProcessor.loadImage(scaledFlag)
//                .get();
//
//        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
//    }
//
//    @Test
//    public void contrast() throws Exception {
//        BufferedImage normalFlag = imageLoader.loadBufferedImage("normalFlag");
////        String scaledFlag = getClass().getResource("/images/scaledFlag.png").getFile();
//
//        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
//                .contrast(0.5f)
//                .get();
//
////        ImageIO.write(actualImage, "PNG", new File("c:\\Users\\pipiczi\\Documents\\contrasted.png"));
//
////        BufferedImage expectedImage = ImageProcessor.loadImage(scaledFlag)
////                .get();
////
////        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
//    }

}
