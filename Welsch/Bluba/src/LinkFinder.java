import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder implements Runnable {

    private String url;
    private ILinkHandler linkHandler;
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

                for (int i = 0; i < nodeList.size(); i++) {
                    ImageTag lt = (ImageTag) nodeList.elementAt(i);
                    System.out.println(lt.getImageURL());
                    URL urlsadfas = new URL(lt.getImageURL());
                    InputStream in = new BufferedInputStream(urlsadfas.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1!=(n=in.read(buf)))
                    {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();

                    FileOutputStream fos = new FileOutputStream("C:\\Schule\\POS\\Welsch\\Besondere Bilder\\1.Klasse");
                    fos.write(response);
                    fos.close();
            }
        } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
}

