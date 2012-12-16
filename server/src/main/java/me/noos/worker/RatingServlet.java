package me.noos.worker;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.noos.engine.recommendation.collaborative.data.BaseDataset;
import me.noos.engine.recommendation.collaborative.data.DiggData;
import me.noos.engine.recommendation.collaborative.data.RatingBuilder;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.collaborative.model.Rating;
import me.noos.engine.recommendation.collaborative.model.User;
import me.noos.engine.recommendation.collaborative.recommender.DiggDelphi;
import me.noos.engine.recommendation.content.rss.DiggStoryItem;
import me.noos.model.Model;

public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = -2691763071179461336L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Model model = Model.getInstance();
		
		String username = req.getParameter("user");
		String itemIdString = req.getParameter("itemid");
		String ratingString = req.getParameter("rating");
		
		int itemId = Integer.parseInt(itemIdString);
		int ratingValue = Integer.parseInt(ratingString);
		
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
		
		// identify our user
		List<User> users = new ArrayList<User>(ds.getUsers());
		User user = ds.getUser(1);		// shouldn't initialize like this, but doing for demo
		for (User u : users) {
			if (u.getName().toLowerCase().equals(username)) {
				user = u;
			}
		}
		
		// find the story
		Item item = ds.getItem(1);		// ditto - shouldn't initialize like this, but doing for demo
		for (Item i : ds.getItems()) {
			if (i.getId() == itemId) {
				item = i;
			}
		}
		
		 Rating rating = new Rating(user.getId(), item.getId(), ratingValue);
         rating.setItem(item);
      	  user.addRating(rating);
      	  
      	  ds.add(user);
      	 user.addUserContent(item.getItemContent());
      	  
      	  System.out.println("User \"" + user.getName() + "\" submitted a rating of value " + rating.getRating() + " for story \"" + item.getName() + "\"");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
	}
}
