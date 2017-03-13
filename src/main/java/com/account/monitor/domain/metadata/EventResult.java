package com.account.monitor.domain.metadata;

/**
 * Default Constructor will created a simple OK result, status = OK, message = "OK"
 */
public class EventResult implements ConfigurableImmutable{
    /**
     * Instance for simple use
     */
    public static final EventResult OK = new ImmutableEventResult();
    private String resultMsg = "OK";
    private EventStatusType status = EventStatusType.OK;
    protected boolean isImmutable = false;

    public EventResult(EventStatusType type, String resultMessage) {
        this.resultMsg = resultMessage;
        this.status = type;
    }

    /**
     * Defaut event resut is resultMsg = OK and status = OK
     */
    public EventResult() {

    }

    public String getResultMessage() {
        return resultMsg;
    }

    public void setResultMessage(String result) {
    	immutableCheck();
        this.resultMsg = result;
    }

    /**
     * The status for this request
     * @return
     */
    public EventStatusType getStatus() {
        return status;
    }

    public void setStatus(EventStatusType status) {
    	immutableCheck();
        this.status = status;
    }

    /**
     * Easy way to set a result to NOK, just invoke this method. Status will be set to NOK, and ResultMessage will be set to "NOK"
     */
    protected void fail() {
    	immutableCheck();
        this.status = EventStatusType.NOK;
        this.resultMsg = "NOK";
    }

    /**
     * Easy way to set a result to NOK, just invoke this method. Status will be set to NOK, and ResultMessage will be set to the given message
     */
    public void fail(String failMsg) {
    	immutableCheck();
        this.status = EventStatusType.NOK;
        this.resultMsg = failMsg;
    }

	@Override
	public boolean isImmutable() {
		return isImmutable;
	}

	@Override
	public void setToImmutable() {
		isImmutable = true;
	}
	
	/**
	 * If instance is immutable, throw an UnsupportedOperationException
	 */
	protected void immutableCheck(){
		if(isImmutable){
    		throw new UnsupportedOperationException("Immutable object can not change");
    	}
	}

}
