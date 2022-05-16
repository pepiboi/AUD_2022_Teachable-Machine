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
                NodeList tagList = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                List<String> urls = new ArrayList<>();
                BufferedImage bImage=null;
                for (int i = 0; i < nodeList.size(); i++) {
                    ImageTag ltt = (ImageTag) nodeList.elementAt(i);

                    LinkTag lt = (LinkTag) tagList.elementAt(i);

                    if (!lt.getLink().isEmpty() && !linkHandler.visited(lt.getLink())) {
                        urls.add(lt.getLink());
                    }

                    if (!ltt.getImageURL().isEmpty() && !linkHandler.visited(ltt.getImageURL())) {
                        bImage= ImageIO.read(new URL(ltt.getImageURL()));
                        ImageIO.write(bImage,"jpg", new File("C:\\Schule\\POS\\Welsch\\Besondere Bilder\\1.Klasse"));
                        count++;
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

