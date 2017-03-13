package com.account.monitor.domain.condition;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ColumnCondition {
	private Condition condition;
	private String columnName;
	
	public ColumnCondition(String columnName, Condition condition){
		this.setColumnName(columnName);
		this.setCondition(condition);
	}
	public ColumnCondition(){}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String[] createSQLWhereClause() {
		return this.condition.createSQLWhereClause(columnName);
	}

	public static String generateGroupsList(List<ColumnCondition> conditions, String connector) {

		List<String> groupStringCondition = new ArrayList<>();

		connector = " " + connector + " ";
		for (ColumnCondition condition : conditions) {
			String groupTemp = condition.createSQLWhereClause()[0];
			groupStringCondition.add(groupTemp);
		}
		return StringUtils.join(groupStringCondition, connector);
	}

	public static List<String> getAllConditionParameters(List<ColumnCondition> conditions) {
		List<String> allParameters = new ArrayList<>();
		if(conditions==null || conditions.size()==0){
			return allParameters;
		}
		for (ColumnCondition condition : conditions) {
			String[] words = condition.createSQLWhereClause();
			for (int i = 1; i < words.length; i++) {
				allParameters.add(words[i]);
			}
		}
		return allParameters;
	}
	
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		
		if(o instanceof ColumnCondition){
			return StringUtils.equals(((ColumnCondition)o).columnName, this.columnName) &&
					StringUtils.equals(conditionUniqueString(((ColumnCondition)o).condition), conditionUniqueString(this.condition));
		}
		
		return false;
	}
	
	private String conditionUniqueString(Condition c){
		String conditionStr = "";
		if(c == null){
			conditionStr += "null";
		}else{
			conditionStr += c.getClass().getName();
		}
		
		conditionStr += "-";
		
		if(c.getConditionValue() == null){
			conditionStr += "null";
		}else{
			conditionStr += "-" + c.getConditionValue().toString();
		}
		
		return conditionStr;
	}
	
	public int hashCode(){
		return (this.columnName + "-" + (this.condition == null? "null" : (condition.getConditionValue() == null ? "null" : condition.getConditionValue().toString())))
				.hashCode();
	}
	
	public String toString(){
		return "ColumnCondtion, colName:["+this.columnName+"], contiontion:["+this.condition+"]]";
	}
}


