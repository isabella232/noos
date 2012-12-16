package me.noos.engine.webcrawler.parser.common;

import me.noos.engine.webcrawler.parser.html.HTMLDocumentParser;

public class DocumentParserFactory {
    
    private static DocumentParserFactory instance = new DocumentParserFactory();
    
    public static DocumentParserFactory getInstance() {
        return instance;
    }
    
    private DocumentParserFactory() {
        // empty
    }
    
    /**
     * Returns an instance of the <code>DocumentParser</code> based 
     * on the document type.
     * 
     * @param type document type.
     * @return
     * @throws DocumentParserException
     */
    public DocumentParser getDocumentParser(String type) 
        throws DocumentParserException {
        if( "text/html".equalsIgnoreCase(type) ) {
            return new HTMLDocumentParser();
        }
        else {
            throw new DocumentParserException("Unsupported document type: '"+ type + "'.");
            
        }
    }
}
