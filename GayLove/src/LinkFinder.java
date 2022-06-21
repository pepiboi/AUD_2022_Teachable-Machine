
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder {
    private static String url= "https://www.google.com/search?q=koi&tbm=isch";
    private static int count = 0;
    private static String lastSearchItem = "koi";
    private static String path;

    public void run(String searchItem, String klasse){
        url = url.replace(lastSearchItem, searchItem);
        lastSearchItem = searchItem;
      getSimpleLinks(url, klasse);
    }

    //Methode 2
    /*private void method() {
        String url = "https://www.google.com/search?q=thunfisch+fisch&tbm=isch&hl=de&chips=q:thunfisch+fisch,online_chips:tuna:xDkCJUwzpdI%3D&client=firefox-b-d&sa=X&ved=2ahUKEwjxo6jAt_X3AhUmgv0HHdZcDIoQ4lYoAHoECAEQHQ&biw=1903&bih=938";
        try {

            //connect to the website and get the document
            Document document = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .get();

            //select all img tags
            Elements imageElements = document.select("img");

            //iterate over each image
            for (Element imageElement : imageElements) {

                //make sure to get the absolute URL using abs: prefix
                urlsP.add(imageElement.attr("abs:src"));

            }
            System.out.println("number of results: " + urlsP.size());
            download();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void download() {
        System.out.println("Download start");
        BufferedImage bImage = null;
        StringBuilder sb = new StringBuilder();
        File outputfile;
        try {
            for (String imageUrl : urlsP) {
                System.out.println(imageUrl);

                bImage = ImageIO.read(new URL(imageUrl));
                sb.append("C:\\Schule\\POS\\Welsch\\Besondere Bilder\\1.Klasse\\");
                sb.append("bild").append(count);
                sb.append(".jpg");
                outputfile = new File(sb.toString());
                ImageIO.write(bImage, "jpg", outputfile);
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public void setPath(String path){
        LinkFinder.path = path;
    }

    //Methode 1
    private static void getSimpleLinks(String url,String klasse) {
        try {
            Parser parser = new Parser(url);
            NodeList nodeList = parser.extractAllNodesThatMatch(new NodeClassFilter(ImageTag.class));
            BufferedImage bImage = null;
            StringBuilder sb = new StringBuilder();
            File outputfile;
            File temp;

            for (int i = 1; i < nodeList.size(); i++) {
                    ImageTag lt = (ImageTag) nodeList.elementAt(i);
                    if (!lt.getImageURL().isEmpty() && count < 251) {
                        System.out.println(lt.getImageURL());

                        bImage = ImageIO.read(new URL(lt.getImageURL()));
                        sb.append( path + "\\"  + klasse + "\\");

                        temp = new File(sb.toString());
                        temp.mkdir();

                        sb.append("bild").append(count);
                        sb.append(".jpg");

                        outputfile = new File(sb.toString());
                        ImageIO.write(resizeImage(bImage), "jpg", outputfile);
                        count++;
                        sb = new StringBuilder();
                    }
                count=0;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
