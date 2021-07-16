package com.example.cicd.demo.helper;

import java.util.Map;

import org.springframework.data.domain.Sort;

public interface ITodoListServiceHelper {
	
	public Map<String, String> parseSortBy(String sortBy);
	
	public Sort parseQueryMap(Map<String, String> queryMap);
	
	public Sort buildSort(String sortBy);
	
}
