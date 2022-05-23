import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder implements Runnable {

    private String url;
    private ILinkHandler linkHandler;
    private static int count;
    /**
     * Used fot statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinder(String url, ILinkHandler handler) {
        //ToDo: Implement Constructor
        this.url = url;
        this.linkHandler = handler;
    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }

    private void getSimpleLinks(String url) {
        // ToDo: Implement Webcrawler6
        if (!linkHandler.visited(url)) {

            try {
                Parser parser = new Parser(url);
                NodeList nodeList = parser.extractAllNodesThatMatch(new NodeClassFilter(ImageTag.class));
                List<String> urls = new ArrayList<>();
                BufferedImage bImage=null;
                StringBuilder sb = new StringBuilder();
                File outputfile;
                for (int i = 0; i < nodeList.size(); i++) {
                    ImageTag lt = (ImageTag) nodeList.elementAt(i);

                    if (!lt.getImageURL().isEmpty() && !linkHandler.visited(lt.getImageURL())) {
                        System.out.println("hallo");
                        urls.add(lt.getImageURL());
                        System.out.println(lt.getImageURL());

                        bImage= ImageIO.read(new URL(lt.getImageURL()));

                        sb.append("C:\\Schule\\POS\\Welsch\\Besondere Bilder\\4.Klasse\\");
                        sb.append("bild").append(count);
                        sb.append(".jpg");
                        outputfile= new File(sb.toString());
                        ImageIO.write(bImage,"jpg",outputfile);
                        count++;
                        sb= new StringBuilder();
                    }
                }
                linkHandler.addVisited(url);
                if (linkHandler.size() == 1500) {
                    System.out.println("Time elapsed: " + (System.nanoTime() - t0));
                    System.out.println("---------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n-------------------------------------------------------------" +
                            "\n------------------------------------------------------------------");
                }

                for (String l : urls) {
                    linkHandler.queueLink(l);
                }

        } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
}

