package com.example.cicd.core.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static com.example.cicd.core.enums.LogStatement.LOCK;
import static com.example.cicd.core.enums.LogStatement.SKIP;
import static com.example.cicd.core.enums.LogStatement.UNLOCK;
import static java.lang.String.format;
import static java.lang.management.ManagementFactory.getMemoryMXBean;
import static java.lang.management.ManagementFactory.getThreadMXBean;
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords;

import java.io.File;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.cicd.core.service.IHealthDetectService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class HealthDetectServiceImpl implements IHealthDetectService {
	
	private ReentrantLock diskFairLock = new ReentrantLock(true);
	
	private ReentrantLock heapMemoryFairLock = new ReentrantLock(true);
	
	private ReentrantLock threadFairLock = new ReentrantLock(true);
	
	@Override
	public void disk() {
		if (!diskFairLock.tryLock()) {
			log.debug(DEFAULT.value(), () -> SKIP.value());
			return;
		}
		try {
			log.debug(DEFAULT.value(), () -> LOCK.value());
			
			JSONObject logParams = new JSONObject();
			File root = new File("/");
			long free = root.getFreeSpace();
			long total = root.getTotalSpace();
			long percentage = (total != 0) ? (free * 100 / total) : 0;
			logParams.put("Free Space : ", byteCountToDisplaySize(free));
			logParams.put("Total Space : ", byteCountToDisplaySize(total));
			logParams.put("Remain Percentage : ", format("%s %%", percentage));
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock add sleep 1s
			diskFairLock.unlock();
			log.debug(DEFAULT.value(), () -> UNLOCK.value());
		}
	}
	
	@Override
	public void heapMemory() {
		if (!heapMemoryFairLock.tryLock()) {
			log.debug(DEFAULT.value(), () -> SKIP.value());
			return;
		}
		try {
			log.debug(DEFAULT.value(), () -> LOCK.value());
			
			JSONObject logParams = new JSONObject();
			MemoryUsage heapMemoryUsage = getMemoryMXBean().getHeapMemoryUsage();
			long committed = heapMemoryUsage.getCommitted();
			long max = heapMemoryUsage.getMax();
			long percentage = (max != 0) ? 100 - (committed * 100 / max) : 0;
			logParams.put("Committed Heap Memory : ", byteCountToDisplaySize(committed));
			logParams.put("Max Heap Memory : ", byteCountToDisplaySize(max));
			logParams.put("Remain Percentage : ", format("%s %%", percentage));
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock add sleep 1s
			heapMemoryFairLock.unlock();
			log.debug(DEFAULT.value(), () -> UNLOCK.value());
		}
	}
	
	@Override
	public void thread() {
		if (!threadFairLock.tryLock()) {
			log.debug(DEFAULT.value(), () -> SKIP.value());
			return;
		}
		try {
			log.debug(DEFAULT.value(), () -> LOCK.value());
			
			JSONObject logParams = new JSONObject();
			ThreadMXBean threadMXBean = getThreadMXBean();
			logParams.put("Thread Count : ", threadMXBean.getThreadCount());
			logParams.put("Current Thread Cpu Time : ", formatDurationWords(threadMXBean.getCurrentThreadCpuTime(), true, false));
			logParams.put("Current Thread User Time : ", formatDurationWords(threadMXBean.getCurrentThreadUserTime(), true, false));
			log.info(DEFAULT.value(), () -> logParams);
			
			logParams.clear();
			for (Long threadId : threadMXBean.getAllThreadIds()) {
				JSONObject logDetail = new JSONObject();
				ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
				if (threadInfo == null) { continue; }
				logDetail.put("Thread State : ", threadInfo.getThreadState());
				logDetail.put("Thread Cpu Time : ", formatDurationWords(threadMXBean.getThreadCpuTime(threadId), true, false));
				logParams.put(threadInfo.getThreadName(), logDetail);
			}
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock add sleep 1s
			threadFairLock.unlock();
			log.debug(DEFAULT.value(), () -> UNLOCK.value());
		}
	}
	
}
