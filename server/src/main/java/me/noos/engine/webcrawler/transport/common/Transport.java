package me.noos.engine.webcrawler.transport.common;

import me.noos.engine.webcrawler.model.FetchedDocument;

public interface Transport {
    public FetchedDocument fetch(String url) throws TransportException;
    public void init();
    public void clear();
    public boolean pauseRequired();
}
