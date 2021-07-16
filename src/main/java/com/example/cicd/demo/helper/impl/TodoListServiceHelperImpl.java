package com.example.cicd.demo.helper.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.cicd.core.enums.EntityKey;
import com.example.cicd.core.enums.SortDirection;
import com.example.cicd.demo.helper.ITodoListServiceHelper;

@Component
public class TodoListServiceHelperImpl implements ITodoListServiceHelper {
	
	@Override
	public Map<String, String> parseSortBy(String sortBy) {
		List<String> list = Arrays.asList(sortBy.split(","));
		return list.stream()
					.map(pairs -> pairs.split(":"))
					.filter(ary -> ary.length == 2)
					.collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
	}
	
	@Override
	public Sort parseQueryMap(Map<String, String> queryMap) {
		Sort sort = null;
		for (Entry<String, String> entry : queryMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			boolean isAsc = StringUtils.equalsIgnoreCase(SortDirection.ASC.value(), value);
			boolean isDesc = StringUtils.equalsIgnoreCase(SortDirection.DESC.value(), value);
			
			Sort asc = Sort.by(Sort.Direction.ASC, key);
			Sort desc = Sort.by(Sort.Direction.DESC, key);
			
			if (isAsc) { sort = (sort == null) ? asc : sort.and(asc); }
			if (isDesc) { sort = (sort == null) ? desc : sort.and(desc); }
		}
		return sort;
	}
	
	@Override
	public Sort buildSort(String sortBy) {
		Sort defaultSort = Sort.by(Sort.Direction.DESC, EntityKey.CREATED_AT.value());
		if (StringUtils.isBlank(sortBy)) { return defaultSort; }
		
		Map<String, String> queryMap = parseSortBy(sortBy);
		Sort sort = parseQueryMap(queryMap);
		if (sort == null) { sort = defaultSort; }
		return sort;
	}
	
}
