package me.noos.engine.recommendation.collaborative.recommender;


import java.util.List;

import me.noos.engine.recommendation.collaborative.model.Dataset;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.collaborative.model.SimilarItem;
import me.noos.engine.recommendation.collaborative.model.SimilarUser;
import me.noos.engine.recommendation.collaborative.model.User;

public interface Recommender {

    /**
     * Returns top 5 recommendations for the user.
     * 
     * @param user
     * @return recommended items with predicted ratings.
     */
    public List<PredictedItemRating> recommend(User user);
    
    /**
     * Returns top N recommendations for the user.
     * 
     * @param user 
     * @param topN number of top recommendations to return.
     * @return recommended items with predicted ratings.
     */
    public List<PredictedItemRating> recommend(User user, int topN);    
    
    public double predictRating(User user, Item item);
    
    public SimilarUser[] findSimilarUsers(User user);
    
    public SimilarUser[] findSimilarUsers(User user, int topN);
    
    public SimilarItem[] findSimilarItems(Item item);    

    public SimilarItem[] findSimilarItems(Item item, int topN);    
    
    public Dataset getDataset();
    
    public double getSimilarityThreshold();
    
    public void setSimilarityThreshold(double similarityThreshold);    
}
