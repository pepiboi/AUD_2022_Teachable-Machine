/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resizeimages;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 *
 * @author tamino
 */
public class ResizeImages {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Images must be named the same basename and numbered after it beginning with one increasing by one continually to the last number");
        System.out.println("How many images you want to resize?");
        int imageNumber = scanner.nextInt();
        System.out.println("Paste path of saved images (C\\...)");
        String imagePath = scanner.next();
        System.out.println("Paste name of images");
        String baseImageName = scanner.next();
        
        String[] imageNames = new String[imageNumber];
        for(int i=0;i<imageNumber;i++){
            imageNames[i] = baseImageName + (i+1);
        }
        for (int i = 0; i < imageNumber; i++) {
            try {
                File aktImagePath = new File(imagePath + "\\" + imageNames[i] + ".jpg");
                Image image = ImageIO.read(aktImagePath);
                BufferedImage buffered = (BufferedImage) image;
                if (buffered.getHeight() < 2000 || buffered.getWidth() < 2000 || buffered.getHeight() > buffered.getWidth() * 2 || buffered.getHeight() * 2 < buffered.getWidth()) {
                    ImageIO.write(resizeImage(buffered), "jpg", aktImagePath);
                    System.out.println("image " + imageNames[i] + " resized successfully");
                } else {
                    System.out.println("bad image scaling for image " + imageNames[i]);
                }
            } catch (IOException ex) {
                System.out.println("Wrong path or wrong file format of the picture " + (i+1));
            }
        }
        System.out.println("Resizing finished");
    }

    public static BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, 500, 500, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
