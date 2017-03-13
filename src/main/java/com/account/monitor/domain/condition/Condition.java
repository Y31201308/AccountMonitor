package com.account.monitor.domain.condition;

public interface Condition {
	
	static String getConditionType(Condition c){
		return c.getClass().getSimpleName();
	}

	Object getConditionValue();

    boolean check(Object obj);

    String[] createSQLWhereClause(String columnName);

    enum MultipleConditionsMergeLogic {
        AND, OR;
        public static MultipleConditionsMergeLogic parseMergeLogic(String string){
        	if("AND".equalsIgnoreCase(string) || "&".equalsIgnoreCase(string) || "&&".equalsIgnoreCase(string)){
        		return AND;
        	}else if("OR".equalsIgnoreCase(string) || "|".equalsIgnoreCase(string) || "||".equalsIgnoreCase(string)){
        		return OR;
        	}else{
        		throw new IllegalArgumentException("Unknow merge logic: " + string);
        	}
        }
    }
    
}
