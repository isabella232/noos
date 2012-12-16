
import java.util.ArrayList;
import java.util.List;

import me.noos.engine.recommendation.collaborative.data.BaseDataset;
import me.noos.engine.recommendation.collaborative.data.DiggData;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.collaborative.model.User;
import me.noos.engine.recommendation.collaborative.recommender.DiggDelphi;
import me.noos.engine.recommendation.content.rss.DiggStoryItem;


public class Main {
	public static void main(String[] args) {
//		BaseDataset ds = DiggData.loadData("C:/iWeb2/data/ch03/digg_stories.csv");
//		BaseDataset ds = DiggData.loadData("C:/iWeb2/data/ch03/king5_stories.csv");
		BaseDataset ds = DiggData.loadData("C:/Servers/jboss-5.1.0.GA/bin/king5_stories.csv");
//		BaseDataset ds = DiggData.loadData("C:/Servers/jboss-5.1.0.GA/bin/digg_stories.csv");
//		BaseDataset ds = DiggData.loadData("C:/iWeb2/data/ch03/all_stories.csv");
		
		// 1 - charles - generic reader
		// 2 - bob - tech reader
		// 3 - alice - tech reader
		// 4 - jimmy - sports fan
		// 5 - rachel - sports fan
		// 6 - joe - new user
		User user = ds.getUser(4);
		
		List<DiggStoryItem> techStories = new ArrayList<DiggStoryItem>();
		DiggStoryItem diggStory;
		for (Item item : ds.getItems()) {
			if (item instanceof DiggStoryItem) {
				diggStory = (DiggStoryItem) item;
				if (diggStory.getCategory().equals("Technology")) {
					techStories.add(diggStory);
					
					if (techStories.size() >= 10) {
						break;
					}
				}
			}
		}
		
		for (DiggStoryItem d : techStories) {
			System.out.println(d.getCategory() + ": " + d.getTitle());
		}
		
		DiggDelphi delphi = new DiggDelphi(ds);
		System.out.println("XXXXXXXX");
		delphi.findSimilarUsers(user);
//		List<PredictedItemRating> recommendedItems = delphi.recommend(user);
//		System.out.println("YYYYYYYY");
//		PredictedItemRating.printUserRecommendations(user, ds, recommendedItems);
//		System.out.println("ZZZZZZZZ");
	}
}
