package com.example.cicd.demo.helper;

import java.util.Map;

import org.springframework.data.domain.Sort;

public interface ITodoListServiceHelper {
	
	/**
	 * parse incoming sort by key word
	 * 
	 * @param sortBy sort by
	 * @return map with column key and direction value
	 */
	public Map<String, String> parseSortBy(String sortBy);
	
	/**
	 * parse map to Sort object
	 * 
	 * @param queryMap map with column key and direction value
	 * @return Sort object
	 */
	public Sort parseQueryMap(Map<String, String> queryMap);
	
	/**
	 * build sort object
	 * 
	 * @param sortBy sort by key word
	 * @return Sort object
	 */
	public Sort buildSort(String sortBy);
	
}
