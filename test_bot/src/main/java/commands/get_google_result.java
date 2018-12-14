package commands;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import util.STATICS;

import java.net.URLDecoder;
import java.net.URLEncoder;


public class get_google_result {

    public String google_result(String to_google) throws Exception {

        String google = "http://www.google.com/search?q=";
        String charset = "UTF-8";
        String userAgent = "Alfred Discord Bot, version " + STATICS.VERSION ;

        Elements links = Jsoup.connect(google + URLEncoder.encode(to_google, charset)).userAgent(userAgent).get().select(".g>.r>a");

        String results = "";
        int i = 1;
        for (org.jsoup.nodes.Element link : links) {
            String title = link.text();
            String url = link.absUrl("href");
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

            if (!url.startsWith("http")) {
                continue;
            }

            String res = "title: " + title + "\n   URL: " + url + "\n\n";
            results += res;

            i++;
            if(i == 4){
                break;
            }
        }

        return results;
    }
}
