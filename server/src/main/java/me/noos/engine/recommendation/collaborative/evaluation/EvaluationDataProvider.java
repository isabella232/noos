package me.noos.engine.recommendation.collaborative.evaluation;


import java.util.List;

import me.noos.engine.recommendation.collaborative.model.Rating;

/**
 * Interface to access previously generated evaluation data.  
 */
public interface EvaluationDataProvider {
    List<Rating> loadTrainingRatings(int testSize, int testSequence);
    List<Rating> loadTestRatings(int testSize, int testSequence);
}
