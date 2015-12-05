//package com.raspbot.capture;
//
//import static org.bytedeco.javacpp.opencv_core.cvFlip;
//import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
//
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.OpenCVFrameConverter;
//import org.bytedeco.javacv.VideoInputFrameGrabber;
//import org.bytedeco.javacpp.opencv_core.IplImage;
//
//public class Grabber implements Runnable {
//    final int INTERVAL=1000;///you may use interval
//    IplImage image;
//    CanvasFrame canvas = new CanvasFrame("Web Cam");
//    public Grabber() {
//        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//    }
//    @Override
//    public void run() {
//        FrameGrabber grabber = new VideoInputFrameGrabber(0);
//        int i=0;
//        try {
//            grabber.start();
//            IplImage img;
//            while (true) {
//
//                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//                img = converter.convertToIplImage(grabber.grab());
//                if (img != null) {
//                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
//                    cvSaveImage((i++)+"-capture.jpg", img);
//                }
//                Thread.sleep(INTERVAL);
//            }
//        } catch (Exception e) {
//        }
//    }
//}
