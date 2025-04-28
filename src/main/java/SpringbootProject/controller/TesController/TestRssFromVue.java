package SpringbootProject.controller.TesController;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import SpringbootProject.entity.notSaving.ArticleObject;

@RestController
public class TestRssFromVue {
	
	/*
	 * Xử lý Proxy từ rss
	 * Truyền xml để vue xử lý xml
	 * */
	@GetMapping("/rss-xml-process")
    public ResponseEntity<String> getRssXmlFeed() {
        try {
            String rssUrl = "https://vnexpress.net/rss/kinh-doanh.rss";
            RestTemplate restTemplate = new RestTemplate();
            String rssData = restTemplate.getForObject(rssUrl, String.class);

            if (rssData == null) {
                return ResponseEntity.status(500).body("Dữ liệu RSS rỗng.");
            }

            return ResponseEntity.ok(rssData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi lấy dữ liệu RSS: " + e.getMessage());
        }
    }
	
	
	@GetMapping("/rss")
    public ResponseEntity<List<ArticleObject>> getRssFeed() {
        try {
            String rssUrl = "https://vnexpress.net/rss/kinh-doanh.rss";
            RestTemplate restTemplate = new RestTemplate();
            String rssData = restTemplate.getForObject(rssUrl, String.class);

            if (rssData == null) {
                return ResponseEntity.status(500).body(null);
            }

            // Phân tích XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(rssData.getBytes()));
            document.getDocumentElement().normalize();

            NodeList items = document.getElementsByTagName("item");
            List<ArticleObject> articles = new ArrayList<>();

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                String title = getElementText(item, "title", "Không có tiêu đề");
                String link = getElementText(item, "link", "#");
                String pubDate = getElementText(item, "pubDate", "Không có ngày đăng");
                String description = getElementText(item, "description", "Không có mô tả");
                String guid = getElementText(item, "guid", String.valueOf(Math.random()));

                // Loại bỏ CDATA và HTML tags từ description
                description = description.replaceAll("<!\\[CDATA\\[(.*?)\\]\\]>", "$1").replaceAll("<[^>]+>", "");

                // Lấy hình ảnh từ enclosure hoặc description
                String image = null;
                Element enclosure = (Element) item.getElementsByTagName("enclosure").item(0);
                if (enclosure != null) {
                    image = enclosure.getAttribute("url");
                }
                if (image == null) {
                    // Thử lấy từ description
                    String[] parts = description.split("src=\"");
                    if (parts.length > 1) {
                        image = parts[1].split("\"")[0];
                    }
                }

                ArticleObject article = new ArticleObject(title, link, pubDate, description, image, guid);
                System.out.println("pubDate: "+pubDate);
                articles.add(article);
            }

            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    private String getElementText(Element parent, String tagName, String defaultValue) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : defaultValue;
    }
	
}
