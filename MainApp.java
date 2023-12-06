import com.lib.imageProcesso.*;
public class MainApp {
    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();

        // Acquire image from camera
        Mat image = processor.acquireImageFromCamera(0);

        // Inspect image properties
        ImageProcessor.ImageProperties properties = processor.inspectImage(image);
        System.out.println("Image width: " + properties.width);
        System.out.println("Image height: " + properties.height);

        // Save image to file
        processor.saveImageToFile(image, "output.jpg", "JPEG");

        // Crop image to a rectangle
        Mat croppedImage = processor.cropImage(image, 100, 50, 200, 150);

        // ... Perform further processing on the cropped image
    }
}
