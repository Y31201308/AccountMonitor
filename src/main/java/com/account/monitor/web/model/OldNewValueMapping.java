package com.account.monitor.web.model;

public class OldNewValueMapping<T> {
    public OldNewValueMapping(){}
    public OldNewValueMapping(T oldValue, T newValue){
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public T oldValue, newValue;


    public OldNewValueMapping<T> clone(){
        return new OldNewValueMapping<T>(this.oldValue, this.newValue);
    }

    public String toString(){
        return "[old["+oldValue+"]-new["+newValue+"]]";
    }
}

