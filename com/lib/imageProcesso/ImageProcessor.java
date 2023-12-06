package com.lib.imageProcesso;


public class  ImageProcessor {
    static {
        System.loadLibrary("libImageProcessing"); // Replace with actual library name
    }

    // Function signatures for image acquisition
    public native long acquireImageFromCamera(int cameraIndex);
    public native long acquireImageFromFile(String filePath);

    // Function signature for image inspection
    public native ImageProperties inspectImage(long imageHandle);

    // Function signatures for image saving
    public native boolean saveImageToFile(long imageHandle, String filePath, String format);

    // Function signature for image cropping
    public native long cropImage(long imageHandle, int xStart, int yStart, int width, int height);

    // Function to release image resources
    public native void releaseImage(long imageHandle);

    // Convenience method for acquiring and inspecting an image from a source
    public ImageProperties acquireAndInspectImage(String source) {
        long imageHandle = 0;
        if (source.startsWith("camera:")) {
            int cameraIndex = Integer.parseInt(source.substring(7));
            imageHandle = acquireImageFromCamera(cameraIndex);
        } else {
            imageHandle = acquireImageFromFile(source);
        }
        if (imageHandle == 0) {
            throw new RuntimeException("Failed to acquire image");
        }
        try {
            return inspectImage(imageHandle);
        } finally {
            releaseImage(imageHandle);
        }
    }

    // Convenience method for saving a cropped image to a file
    public void saveCroppedImage(String source, String filename, String format, int xStart, int yStart, int width, int height) {
        long imageHandle = acquireImageFromFile(source);
        if (imageHandle == 0) {
            throw new RuntimeException("Failed to acquire image");
        }
        try {
            long croppedImageHandle = cropImage(imageHandle, xStart, yStart, width, height);
            boolean success = saveImageToFile(croppedImageHandle, filename, format);
            if (!success) {
                throw new RuntimeException("Failed to save image");
            }
            releaseImage(croppedImageHandle);
        } finally {
            releaseImage(imageHandle);
        }
    }

    public static class ImageProperties {
        public int width;
        public int height;
        public String format;
        public int colorSpace; // Replace with actual data type based on C++ library
    }
}
