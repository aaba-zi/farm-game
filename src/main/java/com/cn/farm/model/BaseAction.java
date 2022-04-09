package com.cn.farm.model;

/**
 * The interface basic actions for purchase and sell
 */
public interface BaseAction {
    /**
     * purchase method
     *
     * @return the boolean
     */
    public boolean purchase();

    /**
     * sell method
     *
     * @return the boolean
     */
    public boolean sell();
}
