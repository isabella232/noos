package me.noos.worker;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.noos.engine.recommendation.collaborative.data.BaseDataset;
import me.noos.engine.recommendation.collaborative.data.DiggData;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.content.rss.DiggStoryItem;
import me.noos.model.Model;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class CategoryServlet extends HttpServlet{
	private static final long serialVersionUID = -8868904367849073933L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Model model = Model.getInstance();
		String category = req.getParameter("cat");
		
		// get our dataset
		BaseDataset ds = model.getDataset();
		if (ds == null) {
			ds = DiggData.loadData("king5_stories.csv");
			model.setDataset(ds);
		}
		
		List<DiggStoryItem> stories = new ArrayList<DiggStoryItem>();
		DiggStoryItem diggStory;
		
		for (Item item : ds.getItems()) {
			if (item instanceof DiggStoryItem) {
				diggStory = (DiggStoryItem) item;
				if (diggStory.getCategory().toLowerCase().equals(category)) {
					stories.add(diggStory);
					
					if (stories.size() >= 10) {
						break;
					}
				}
			}
		}
		
//		PrintWriter out = resp.getWriter();
//		for (DiggStoryItem d : techStories) {
//			out.println(d.getCategory() + ": " + d.getTitle() + "<img src=\"" + d.getImage() + "\"/>");
//		}
		
		
		
		
		
		
		
		// Set up the feed
		SyndFeed syndFeed = new SyndFeedImpl();
		syndFeed.setFeedType("rss_2.0");
		syndFeed.setEncoding("windows-1252");
		syndFeed.setTitle("Noos Feed");
		syndFeed.setLink("http://www.noos.com/rss");
		syndFeed.setDescription("News you want!");
		
		// Populate the feed
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		SyndEntry entry;
		SyndContent description;
		for (DiggStoryItem story : stories) {
			entry = new SyndEntryImpl();
			
			entry.setTitle(story.getTitle());
			entry.setLink(story.getLink());
			entry.setAuthor("StoryID:" + story.getId());		// HACK! -> hiding the story ID in the author field
//					entry.setPublishedDate(story.getPublishedDate());
//					entry.setUpdatedDate(story.getUpdatedDate());
			description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue(story.getDescription());
			entry.setDescription(description);
			
			entries.add(entry);
		}  // for loop
		syndFeed.setEntries(entries);
		
		// Output the feed
		resp.setContentType("application/rss+xml");
		SyndFeedOutput output = new SyndFeedOutput();
		Writer writer = new OutputStreamWriter(resp.getOutputStream());
		
		try {
			output.output(syndFeed, writer);
		} catch (FeedException e) {
//					log.severe("Error outputting stream - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
}
