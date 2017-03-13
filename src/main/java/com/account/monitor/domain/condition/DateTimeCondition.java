package com.account.monitor.domain.condition;

import com.account.monitor.domain.unify.TimeFormat;
import org.joda.time.DateTime;

/**
 * Use to compare the data time. The given date time should follow format "YYYY-MM-dd HH:mm:ss.SSS Z"<br/>
 * Use TimeFormat to parse any time object to valid format.
 * 
 * Don't use this, create new: DataTimeBefore, DataTimeAfter, DataTimeBetween condition for data time compare
 * 
 * @author pu
 *
 */
public class DateTimeCondition implements Condition {

	private enum Type{
		BEFORE, AFTER, BETWEEN
	}
	
	private Type compareType;
	private String dateTime1, dateTime2;
	private DateTime dateTimeObj1, dateTimeObj2;

	private DateTimeCondition() {
		
	}

	public static DateTimeCondition before(String dataTime){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.BEFORE;
		condition.dateTime2 = dataTime;
		condition.dateTimeObj2 = TimeFormat.parseTime(dataTime);
		return condition;
	}


	public static DateTimeCondition before(DateTime dataTime){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.BEFORE;
		condition.dateTimeObj2 = dataTime;
		condition.dateTime2 = TimeFormat.formatTime(dataTime);
		return condition;
	}

	public static DateTimeCondition after(String dataTime){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.AFTER;
		condition.dateTime1 = dataTime;
		condition.dateTimeObj1 = TimeFormat.parseTime(dataTime);
		return condition;
	}

	public static DateTimeCondition after(DateTime dataTime){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.AFTER;
		condition.dateTime1 = TimeFormat.formatTime(dataTime);
		condition.dateTimeObj1 = dataTime;
		return condition;
	}

	public static DateTimeCondition between(String dataTime1, String dataTime2){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.BETWEEN;
		condition.dateTimeObj1 = TimeFormat.parseTime(dataTime1);
		condition.dateTimeObj2 = TimeFormat.parseTime(dataTime2);
		// Ensure dateTimeObj1 is eariler one
		if(condition.dateTimeObj1.isAfter(condition.dateTimeObj2)){
			DateTime tmp = condition.dateTimeObj1;
			condition.dateTimeObj1 = condition.dateTimeObj2;
			condition.dateTimeObj2 = tmp;
		}
		
		condition.dateTime1 = TimeFormat.formatTime(condition.dateTimeObj1);
		condition.dateTime2 = TimeFormat.formatTime(condition.dateTimeObj2);
		return condition;
	}

	public static DateTimeCondition between(DateTime dataTime1, DateTime dataTime2){
		DateTimeCondition condition = new DateTimeCondition();
		condition.compareType = Type.BETWEEN;
		condition.dateTimeObj1 = dataTime1;
		condition.dateTimeObj2 = dataTime2;
		if(condition.dateTimeObj1.isAfter(condition.dateTimeObj2)){
			DateTime tmp = condition.dateTimeObj1;
			condition.dateTimeObj1 = condition.dateTimeObj2;
			condition.dateTimeObj2 = tmp;
		}
		
		condition.dateTime1 = TimeFormat.formatTime(condition.dateTimeObj1);
		condition.dateTime2 = TimeFormat.formatTime(condition.dateTimeObj2);
		return condition;
	}	
	
	@Override
	public boolean check(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof String) {
			return TimeFormat.parseTime((String)obj) == null ? false : true;
		}
		return false;
	}

	@Override
	public String[] createSQLWhereClause(String columnName) {
		String[] returnString =null;
		if (this.compareType == Type.BEFORE){
			returnString = new String[2];
			returnString[0] = "`" + columnName + "` <= ? ";
			returnString[1] = dateTime2;
		}else if (this.compareType == Type.AFTER){
			returnString = new String[2];
			returnString[0] = "`" + columnName + "` >= ? ";
			returnString[1] = dateTime1;
		}else if (this.compareType == Type.BETWEEN){
			returnString = new String[3];
			returnString[0] = "`" + columnName + "` >= ? AND `" + columnName + "` =< ?";
			returnString[1] = dateTime1;
			returnString[2] = dateTime2;
		}
		return returnString;
	}


	@Override
	public Object getConditionValue() {
		return null;
	}

    public boolean equals(Object o){
    	if(o == null){
    		return false;
    	}
    	
    	if(!(o instanceof DateTimeCondition)){
    		return false;
    	}

    	return false;
    }
}


