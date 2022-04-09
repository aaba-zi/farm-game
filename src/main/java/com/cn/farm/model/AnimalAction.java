package com.cn.farm.model;

/**
 * The interface animal actions
 */
public interface AnimalAction extends BaseAction {
    /**
     * feed
     *
     * @param feed the feed
     * @return the boolean
     */
    public boolean feed(Feed feed);
    
    public boolean play();
}
