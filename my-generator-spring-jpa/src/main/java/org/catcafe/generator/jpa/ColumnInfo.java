package org.catcafe.generator.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

public class ColumnInfo {
	public static final String FORMAT_DATE = "DATE";
	public static final String FORMAT_TIME = "TIME";
	public static final String FORMAT_TIMESTAMP = "TIMESTAMP";
	
	// 表字段索引
	private int index;
	// 表字段名称
	private String field;
	// 表字段类型
	private String type;
	// 注释
	private String comment;
	// 是否主键
	private boolean pk;
	// 是否必须
	private boolean require;
	// 字段长度
	private String length;
	// 格式（null, DATA, TIMESTAMP）
	private String format;
	// 是否Lob格式
	private boolean lob;
	// 对应java类全名
	private String javaClass;
	// 对应java类名
	private String javaClassName;
	// Java属性名称
	private String javaAttributeName;
	// Java方法中用的名称，首字母大写
	private String javaMethodName;
	// 字段值
	private Object value;

	public void process() {
		/**
		 * 处理java关键字
		 */
		//this.field = processJavaKeyWord(this.field);
		this.javaAttributeName = dbNameJavaName(this.field);
		this.javaMethodName = toUpperCaseFirstOne(this.javaAttributeName);
		
		/**
		 * 处理javaClass(个别读不出type，但无需处理)
		 */
		if(null!=type) {
			switch(this.type){
				case "bit":
				case "tinyint": 
				case "smallint":
				case "mediumint":
				case "int":
				{
					this.javaClass = "java.lang.Integer";
					break;
				}
				case "bigint":
				{
					this.javaClass = "java.lang.Long";
					break;
				}
				case "decimal":
				{
					this.javaClass = "java.math.BigDecimal";
					break;
				}
				case "float":
				{
					this.javaClass = "java.lang.Float";
					break;
				}
				case "double":
				{
					this.javaClass = "java.lang.Double";
					break;
				}
				case "char":
				case "varchar":{
					this.javaClass = "java.lang.String";
					break;
				}
				default :{}
			}
		}
		
		/**
		 * 处理特殊字符
		 */
		this.comment = processSpecialChar(this.comment);
		//this.javaClass = "[B".equals(this.javaClass) ? "java.lang.Byte" : this.javaClass;
		this.lob = "[B".equals(this.javaClass) ? true : false;
		this.javaClass = "[B".equals(this.javaClass) ? "java.lang.Byte" : this.javaClass;
		this.javaClassName = "java.lang.Byte".equals(this.javaClass) ? "byte[]" : this.javaClass.substring(this.javaClass.lastIndexOf(".") + 1);
		//"3,5"这类的长度暂时过滤
		this.length = this.length!=null && this.length.contains(",") ? null : this.length;
		
		/*
		 * 日期处理
		 * java.sql.Time,java.sql.Date,java.sql.Timestamp=>java.util.Date
		 * java.sql.Date=>@Temporal(TemporalType.DATE)
		 * java.sql.Time,java.sql.Timestamp=>@Temporal(TemporalType.TIMESTAMP)
		 */
		if("java.sql.Date".equals(this.javaClass)) {
			this.javaClass = "java.util.Date";
			this.javaClassName = "Date";
			this.format = FORMAT_DATE;
		}
		if("java.sql.Time".equals(this.javaClass) ) {
			this.javaClass = "java.util.Date";
			this.javaClassName = "Date";
			this.format = FORMAT_TIMESTAMP;
			//this.format = FORMAT_TIME;
		}
		if("java.sql.Timestamp".equals(this.javaClass)) {
			this.javaClass = "java.util.Date";
			this.javaClassName = "Date";
			this.format = FORMAT_TIMESTAMP;
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isRequire() {
		return require;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	public String getJavaAttributeName() {
		return javaAttributeName;
	}

	public void setJavaAttributeName(String javaAttributeName) {
		this.javaAttributeName = javaAttributeName;
	}

	public String getJavaMethodName() {
		return javaMethodName;
	}

	public void setJavaMethodName(String javaMethodName) {
		this.javaMethodName = javaMethodName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean isLob() {
		return lob;
	}

	public void setLob(boolean lob) {
		this.lob = lob;
	}

	public static String dbNameJavaName(String dbName) {
		String[] strArr = dbName.split("_");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<strArr.length; i++) {
			String str = strArr[i];
			sb.append(i==0 ? Character.toLowerCase(str.charAt(0)) : Character.toUpperCase(str.charAt(0))).append(str.substring(1));
		}
		String finalStr = processJavaKeyWord(sb.toString());
		return finalStr;
	}
	
	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		return Character.isLowerCase(s.charAt(0)) ? s
				: (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1))
						.toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		return Character.isUpperCase(s.charAt(0)) ? s
				: (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1))
						.toString();
	}

	private static String processSpecialChar(String orig) {
		String[] specialChars = new String[] { "\"" };
		if (StringUtils.isBlank(orig)) {
			return orig;
		} else {
			for (String specialChar : specialChars) {
				if (orig.contains(specialChar)) {
					return orig.replace(specialChar, "\\" + specialChar);
				}
			}
			return orig;
		}
	}
	
	private static String processJavaKeyWord(String orig) {
		String[] javaKeyWords = new String[] { "short", "boolean", "long", "double", "int", "char", "byte", "float" };
		if (StringUtils.isBlank(orig)) {
			return orig;
		} else {
			for (String keyword : javaKeyWords) {
				if (orig.equals(keyword)) {
					//return orig + "_";
					return toUpperCaseFirstOne(orig);
				}
			}
			return orig;
		}
	}
}
