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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            String file = "test";//replace with imagepath
            File imagePath = new File(file);
            Image image = ImageIO.read(new File(file));
            BufferedImage buffered = (BufferedImage) image;
            ImageIO.write(resizeImage(buffered), "jpg", imagePath);
        } catch (IOException ex) {
            System.out.println("Error while resizing images");
        }
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, 500, 500, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
