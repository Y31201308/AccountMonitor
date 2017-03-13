package com.account.monitor.domain.condition;

public class EqualsCondition implements Condition {

    private Object checkObj;

    public EqualsCondition(Object checkObj) {
        this.checkObj = checkObj;
    }

    public String toString(){
    	return "equals to " + checkObj.toString();
    }
    
    @Override
    public boolean check(Object obj) {
        if (obj == null) {
            return false;
        }

        return this.checkObj.equals(obj);
    }
    
    public Object getEqualsTo(){
    	return this.checkObj;
    }

    @Override
    public String[] createSQLWhereClause(String columnName) {
        String[] returnString = new String[2];
        returnString[0] = "`" + columnName + "` = ? ";
        returnString[1] = checkObj.toString();

        return returnString;
    }

	@Override
	public Object getConditionValue() {
		return this.checkObj;
	}

    public boolean equals(Object o){
    	if(o == null){
    		return false;
    	}
    	
    	if(!(o instanceof EqualsCondition)){
    		return false;
    	}
    	
    	if(this.checkObj == null && ((EqualsCondition)o).getConditionValue() == null){
    		return true;
    	}else if(this.checkObj == null || ((EqualsCondition)o).getConditionValue() == null){
    		return false;
    	}else{
    		return ((EqualsCondition)o).getConditionValue().equals(this.checkObj);
    	}
    }

}
