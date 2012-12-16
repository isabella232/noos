/**
 * 
 */
package me.noos.engine.recommendation.collaborative.data;


import java.util.ArrayList;

import me.noos.engine.recommendation.collaborative.model.Content;
import me.noos.engine.recommendation.collaborative.model.Item;
import me.noos.engine.recommendation.collaborative.model.Rating;

/**
 * Item for news dataset.
 */
public class NewsItem extends Item {
	
	/**
     * SVUID
     */
    private static final long serialVersionUID = 6349342365379966975L;
	
	public NewsItem(int id, String name, Content content) {
		super(id, name, new ArrayList<Rating>(3));
		setItemContent(content);
	}

}
