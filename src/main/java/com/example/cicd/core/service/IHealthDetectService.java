package com.example.cicd.core.service;

public interface IHealthDetectService {
	
	/**
	 * check disk
	 */
	public void disk();
	
	/**
	 * check heap memory
	 */
	public void heapMemory();
	
	/**
	 * check thread info
	 */
	public void thread();
	
}
