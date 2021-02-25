import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	public static void main(String[] args) {
		String url = "https://templatemo.com/";
//		crawl(1,url,new ArrayList<String>());
		
		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println("Connected To:" + url);
			ArrayList<Element> image = doc.select("img[src]");
			System.out.println(image.size());
			for (int counter = 0; counter < image.size(); counter++) { 		      
		          System.out.println(image.get(counter).absUrl("src")); 		
		      } 
			
		} catch (IOException e) {
			return;
		}

	}
	private static void crawl(int level,String url,ArrayList<String> visited) {
		if(level<= 5) {
			Document doc = request(url, visited);
			if(doc != null) {
				for(Element link: doc.select("a[href]")) {
					String next_link = link.absUrl("href");
					if(visited.contains(next_link) == false) {
						crawl(level++,next_link,visited); 
					}
				}
			}
		}
	}
	private static Document request(String url, ArrayList<String> v) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if(con.response().statusCode() == 200) {
				System.out.println("Successfully connected to: "+ url);
				System.out.println(doc.title());
				v.add(url);
				return doc;
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}
}
