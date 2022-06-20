import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder{

    private static final String url= "https://www.google.com/search?q=koi&tbm=isch";
    private static int count = 0;
    private final List<String> urlsP = new ArrayList<>();

    public static void main(String[] args){
        getSimpleLinks(url);
    }

    //Methode 2
    private void method() {
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


    //Methode 1
    private static void getSimpleLinks(String url) {

            try {
                Parser parser = new Parser(url);
                NodeList nodeList = parser.extractAllNodesThatMatch(new NodeClassFilter(ImageTag.class));
                BufferedImage bImage = null;
                StringBuilder sb = new StringBuilder();
                File outputfile;
                for (int i = 0; i < nodeList.size(); i++) {
                    if (i != 0) {
                        ImageTag lt = (ImageTag) nodeList.elementAt(i);
                        if (!lt.getImageURL().isEmpty() && count < 251) {
                            System.out.println(lt.getImageURL());

                            bImage = ImageIO.read(new URL(lt.getImageURL()));
                            sb.append("C:\\Schule\\POS\\Welsch\\Besondere Bilder\\1.Klasse\\");
                            sb.append("bild").append(count);
                            sb.append(".jpg");

                            outputfile = new File(sb.toString());
                            ImageIO.write(bImage, "jpg", outputfile);
                            count++;
                            sb = new StringBuilder();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

