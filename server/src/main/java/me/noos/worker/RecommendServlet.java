package me.noos.worker;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.noos.engine.recommendation.collaborative.data.BaseDataset;
import me.noos.engine.recommendation.collaborative.data.DiggData;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.collaborative.model.User;
import me.noos.engine.recommendation.collaborative.recommender.DiggDelphi;
import me.noos.engine.recommendation.collaborative.recommender.PredictedItemRating;
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

public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 7007466196427741623L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Model model = Model.getInstance();

		// say hello to user
//		PrintWriter out = resp.getWriter();
		String name = req.getParameter("name");
//	    out.println("Hello, " + name);
		
	    // get our dataset
		BaseDataset ds = model.getDataset();
		if (ds == null) {
			ds = DiggData.loadData("king5_stories.csv");
			model.setDataset(ds);
		}
		
		// consult the oracle
		DiggDelphi delphi = model.getDelphi();
		if (delphi == null) {
			delphi = new DiggDelphi(ds);
			model.setDelphi(delphi);
		}
		
		// find user
		List<User> users = new ArrayList<User>(ds.getUsers());
		User user = null;
		for (User u : users) {
			if (u.getName().equals(name)) {
				user = u;
			}
		}
		
//		// print recommendations
//	    List<PredictedItemRating> recommendedItems = delphi.recommend(user);
//	    for(PredictedItemRating r : recommendedItems) {
//            out.printf("Item: %-36s, predicted rating: %f\n", ds.getItem(r.getItemId()).getName(), r.getRating(4));
//        }
		
		// get stories
	    List<PredictedItemRating> recommendedItems = delphi.recommend(user);
	    List<DiggStoryItem> stories = new ArrayList<DiggStoryItem>();
		for(PredictedItemRating r : recommendedItems) {
//          out.printf("Item: %-36s, predicted rating: %f\n", ds.getItem(r.getItemId()).getName(), r.getRating(4));
          
          Item i = ds.getItem(r.getItemId());
          if (i instanceof DiggStoryItem) {
        	  DiggStoryItem dsi = (DiggStoryItem)i;
        	  stories.add(dsi);
          }
      }
		
		// if there are no recommendations for this user (possibly a new user, or not enough click data),
		// use random stories from the various categories instead to help build our dataset
		List<Item> allStories = new ArrayList<Item>(ds.getItems());
		while (stories.size() < 10) {
			Random random = new Random();
			stories.add((DiggStoryItem)allStories.get(random.nextInt(stories.size() - 1)));
		}
		
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
//			entry.setPublishedDate(story.getPublishedDate());
//			entry.setUpdatedDate(story.getUpdatedDate());
			description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue(story.getDescription().substring(0, 300));
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
//			log.severe("Error outputting stream - " + e.getMessage());
			e.printStackTrace();
		}  // try-catch statement
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
}
