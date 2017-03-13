package com.account.monitor.domain.condition;

import org.apache.commons.lang3.StringUtils;

public class ContainCondition implements Condition {
    private String containString;

    public ContainCondition(String containString) {
        this.containString = containString;
    }
    

    public String toString(){
    	return "contain " + containString;
    }

    public boolean equals(Object o){
    	if(o == null){
    		return false;
    	}
    	
    	if(!(o instanceof ContainCondition)){
    		return false;
    	}
    	
    	return StringUtils.equals(((ContainCondition)o).getContainingString(), this.containString);
    }
    
    @Override
    public boolean check(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof String) {
            return ((String) obj).toLowerCase().contains(containString.toLowerCase());
        }
        return false;
    }

    @Override
    public String[] createSQLWhereClause(String columnName) {
        String[] returnString = new String[2];
        returnString[0] = "LOCATE( ?, `" + columnName + "`) > 0";
        returnString[1] = containString;

        return returnString;
    }
    
    public String getContainingString(){
    	return containString;
    }


	@Override
	public Object getConditionValue() {
		return containString;
	}

}
