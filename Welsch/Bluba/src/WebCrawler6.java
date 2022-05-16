/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author bmayr
 */
public class WebCrawler6 implements ILinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService execService;

    public WebCrawler6(String startingURL, int maxThreads) {
        this.url = startingURL;
        // ToDo: Register a ThreadPool with "maxThreads" for execService
        execService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link) throws Exception {
        startNewThread(link);
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }

    private void startNewThread(String link) throws Exception {
        // ToDo: Use executer Service to start new LinkFinder Task!
        execService.execute(new LinkFinder(link, this));
    }

    private void startCrawling() throws Exception {        
        startNewThread(this.url);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new WebCrawler6("https://www.google.com/search?q=lol+archer&client=firefox-b-d&sxsrf=ALiCzsbEjZY4gabYC6ep_yfW_5KCo3HvhA:1652089956834&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjjjY7YktL3AhWLiqQKHXkRBdIQ_AUoAXoECAEQAw&biw=1920&bih=955", 64).startCrawling();
    }
}