package me.noos.engine.recommendation.collaborative.evaluation;


import java.io.File;
import java.util.List;

import me.noos.engine.recommendation.collaborative.data.MovieLensDataset;
import me.noos.engine.recommendation.collaborative.model.Dataset;
import me.noos.engine.recommendation.collaborative.model.Rating;
import me.noos.engine.recommendation.collaborative.recommender.Delphi;
import me.noos.engine.recommendation.collaborative.similarity.RecommendationType;
import me.noos.util.config.IWeb2Config;

/**
 * deprecated use <code>MovieLensDelphiRMSE</code> instead.
 */
public class MovieLensRMSE {

    public MovieLensRMSE() {
    }

    public MovieLensDataset createTrainingDataset(int n) {
        String dataDir = IWeb2Config.getHome()+"/data/ch03/MovieLens";
        
        File users = new File(dataDir, MovieLensDataset.USERS_FILENAME);
        File items = new File(dataDir, MovieLensDataset.ITEMS_FILENAME);
        File ratings = new File(dataDir, "u"+n+".base");

        return new MovieLensDataset(users, items, ratings);
    }
    
    public List<Rating> createTestRatings(int n) {
        String dataDir = IWeb2Config.getHome()+"/data/ch03/MovieLens";
        
        File ratings = new File(dataDir, "u"+n+".test");

        return MovieLensDataset.loadRatings(ratings);
    }
    
    public double[] calculate() {

        double similarityThreshold = 0.50;

        int N = 5;
        
        double[] rmse = new double[N];

        RMSEEstimator rmseEstimator = new RMSEEstimator();
        
        for(int i = 1; i <= N; i++) {
        
            Dataset ds = createTrainingDataset(i);
            
            Delphi delphi = new Delphi(ds, RecommendationType.ITEM_BASED);
            delphi.setSimilarityThreshold(similarityThreshold);
            
            List<Rating> testRatings = createTestRatings(i);
            
            double rmseValue = rmseEstimator.calculateRMSE(delphi, testRatings);
            System.out.println(i + ": rmse = " + rmseValue);

            rmse[i-1] = rmseValue;
        }
        
        return rmse;
    }
    
    
    
    public static void main(String[] args) {
        MovieLensRMSE rmse = new MovieLensRMSE();
        rmse.calculate();
    }
}
