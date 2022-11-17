package com.varsity.dgmdashboard.api;


public interface ResponseCallback {
    /**
     * call when user get success result
     *
     * @param object
     */
    public void ResponseSuccessCallBack(Object object);

    /**
     * call when user get fail result
     *
     * @param object
     */
    public void ResponseFailCallBack(Object object);
}
