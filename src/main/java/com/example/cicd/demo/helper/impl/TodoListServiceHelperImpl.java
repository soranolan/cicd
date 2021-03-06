package com.example.cicd.demo.helper.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.cicd.core.enums.EntityKey;
import com.example.cicd.core.enums.SortDirection;
import com.example.cicd.demo.helper.ITodoListServiceHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class TodoListServiceHelperImpl implements ITodoListServiceHelper {
	
	@Override
	public Map<String, String> parseSortBy(String sortBy) {
		List<String> sortByList = asList(sortBy.split(","));
		Map<String, String> sortByMap = sortByList.stream()
													.map(pairs -> pairs.split(":"))
													.filter(ary -> ary.length == 2)
													.collect(toMap(pair -> pair[0], pair -> pair[1]));
		
		JSONObject logParams = new JSONObject();
		logParams.put("sortByList", sortByList);
		logParams.put("sortByMap", sortByMap);
		log.info(DEFAULT.value(), () -> logParams);
		
		return sortByMap;
	}
	
	@Override
	public Sort parseQueryMap(Map<String, String> queryMap) {
		Sort sort = null;
		for (Entry<String, String> entry : queryMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			boolean isAsc = equalsIgnoreCase(SortDirection.ASC.value(), value);
			boolean isDesc = equalsIgnoreCase(SortDirection.DESC.value(), value);
			
			Sort asc = Sort.by(Sort.Direction.ASC, key);
			Sort desc = Sort.by(Sort.Direction.DESC, key);
			
			if (isAsc) { sort = (sort == null) ? asc : sort.and(asc); }
			if (isDesc) { sort = (sort == null) ? desc : sort.and(desc); }
		}
		
		JSONObject logParams = new JSONObject();
		logParams.put("queryMap", queryMap);
		logParams.put("sort", sort);
		log.info(DEFAULT.value(), () -> logParams);
		
		return sort;
	}
	
	@Override
	public Sort buildSort(String sortBy) {
		Sort defaultSort = Sort.by(Sort.Direction.DESC, EntityKey.CREATED_AT.value());
		if (isBlank(sortBy)) { return defaultSort; }
		
		Map<String, String> queryMap = parseSortBy(sortBy);
		Sort sort = parseQueryMap(queryMap);
		if (sort == null) { sort = defaultSort; }
		
		JSONObject logParams = new JSONObject();
		logParams.put("sortBy", sortBy);
		logParams.put("queryMap", queryMap);
		logParams.put("sort", sort);
		log.info(DEFAULT.value(), () -> logParams);
		
		return sort;
	}
	
}
