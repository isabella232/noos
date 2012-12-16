package me.noos.engine.webcrawler.parser.common;

import me.noos.engine.webcrawler.model.FetchedDocument;
import me.noos.engine.webcrawler.model.ProcessedDocument;

/**
 * Interface for parsing document that was retrieved/fetched during
 * collection phase.  
 */
public interface DocumentParser {
    public ProcessedDocument parse(FetchedDocument doc) 
        throws DocumentParserException;
}
