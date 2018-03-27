package org.catcafe.generator.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json类库
 * @author zhangzhentao
 *
 */
public class JsonUtil {
	
	private static ObjectMapper objectMapper;
	
	public static <T> T json2obj(String json, Class<T> clazz){
		try {
			return (T) getObjectMapper().readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Json字符串转换为泛型类型对象
	 * @param json
	 * @param collectionClass 容器对象类
	 * @param elementClasses 容物对象类
	 * @return
	 */
	public static <T> T json2obj(String json, Class<?> collectionClass, Class<?>... elementClasses){
		try {
			JavaType javaType = getCollectionType(collectionClass, elementClasses); 
			return (T) getObjectMapper().readValue(json, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String obj2json(Object obj){
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String obj2json(Object obj, Class<?> collectionClass, Class<?>... elementClasses){
		try {
			JavaType javaType = getCollectionType(collectionClass, elementClasses); 
			return getObjectMapper().writerWithType(javaType).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	/**
	 * 根据容器和容物类获取JavaType对象
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    }
	
	/**
	 * 该处可根据需要返回自定义的ObjectMapper
	 * @return
	 */
	public static ObjectMapper getObjectMapper(){
		if(objectMapper == null){
			objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); //Date类型不作为Timestamp类型编码
			objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true); //安key字母排序
			
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);//空对象的时候不抛错误
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//解码遇到为止属性不报错

			objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//允许不带引号
//			objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
			
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); //编码的时候忽略Null值，json瘦身
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));//日期格式化
			
		}
		return objectMapper;
	}
}
