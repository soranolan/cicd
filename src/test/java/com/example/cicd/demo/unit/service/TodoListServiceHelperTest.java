package com.example.cicd.demo.unit.service;

import static com.example.cicd.core.enums.EntityKey.CREATED_AT;
import static com.example.cicd.core.enums.EntityKey.CREATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.example.cicd.core.enums.SortDirection;
import com.example.cicd.demo.helper.impl.TodoListServiceHelperImpl;

@ExtendWith(MockitoExtension.class)
class TodoListServiceHelperTest {
	
	@InjectMocks
	private TodoListServiceHelperImpl helper;
	
	private String sortBy;
	
	private String createdAt;
	
	private String creator;
	
	private String sortAsc;
	
	private String sortDesc;
	
	@BeforeEach
	void setup() {
		createdAt = CREATED_AT.value();
		creator = CREATOR.value();
		sortAsc = SortDirection.ASC.value();
		sortDesc = SortDirection.DESC.value();
		sortBy = createdAt + ":" + sortDesc;
	}
	
	@Test
	void test_parseSortBy() {
		Map<String, String> expect = new HashMap<String, String>();
		expect.put(createdAt, sortDesc);
		Map<String, String> test = helper.parseSortBy(sortBy);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_parseSortBy_array_length() {
		Map<String, String> expect = new HashMap<String, String>();
		expect.put(createdAt, sortDesc);
		Map<String, String> test = helper.parseSortBy(sortBy + ",column:");
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_parseQueryMap_asc_if_sort_is_null() {
		Sort expect = Sort.by(ASC, createdAt);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(createdAt, sortAsc);
		Sort test = helper.parseQueryMap(queryMap);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_parseQueryMap_asc_if_sort_not_null() {
		Sort expect = Sort.by(ASC, createdAt).and(Sort.by(ASC, creator));
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(createdAt, sortAsc);
		queryMap.put(creator, sortAsc);
		Sort test = helper.parseQueryMap(queryMap);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_parseQueryMap_desc_if_sort_is_null() {
		Sort expect = Sort.by(DESC, createdAt);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(createdAt, sortDesc);
		Sort test = helper.parseQueryMap(queryMap);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_parseQueryMap_desc_if_sort_not_null() {
		Sort expect = Sort.by(DESC, createdAt).and(Sort.by(DESC, creator));
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(createdAt, sortDesc);
		queryMap.put(creator, sortDesc);
		Sort test = helper.parseQueryMap(queryMap);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_buildSort_if_sortBy_is_blank() {
		Sort expect = Sort.by(DESC, createdAt);
		Sort test = helper.buildSort("");
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_buildSort_if_sortBy_is_not_blank() {
		Sort expect = Sort.by(DESC, createdAt);
		Sort test = helper.buildSort(sortBy);
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
	@Test
	void test_buildSort_if_sort_is_null() {
		Sort expect = Sort.by(DESC, createdAt);
		Sort test = helper.buildSort("T:");
		
		assertThat(test).isNotNull().isEqualTo(expect);
	}
	
}
