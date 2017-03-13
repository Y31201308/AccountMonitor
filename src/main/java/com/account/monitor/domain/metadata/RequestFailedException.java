package com.account.monitor.domain.metadata;

public class RequestFailedException extends RuntimeException {

    public RequestFailedException(String msg, Exception cause){
    	super(msg, cause);
    }
    
    public RequestFailedException(String msg) {
        super(msg);
    }

}
