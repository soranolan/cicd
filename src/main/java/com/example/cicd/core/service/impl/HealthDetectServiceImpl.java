package com.example.cicd.core.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static com.example.cicd.core.enums.LogStatement.LOCK;
import static com.example.cicd.core.enums.LogStatement.SKIP;
import static com.example.cicd.core.enums.LogStatement.UNLOCK;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
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
			logParams.put("Free Space : ", FileUtils.byteCountToDisplaySize(free));
			logParams.put("Total Space : ", FileUtils.byteCountToDisplaySize(total));
			logParams.put("Remain Percentage : ", String.format("%s %%", percentage));
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock >>> try { TimeUnit.SECONDS.sleep(1L); } catch (InterruptedException e) { e.printStackTrace(); }
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
			MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
			long committed = heapMemoryUsage.getCommitted();
			long max = heapMemoryUsage.getMax();
			long percentage = (max != 0) ? 100 - (committed * 100 / max) : 0;
			logParams.put("Committed Heap Memory : ", FileUtils.byteCountToDisplaySize(committed));
			logParams.put("Max Heap Memory : ", FileUtils.byteCountToDisplaySize(max));
			logParams.put("Remain Percentage : ", String.format("%s %%", percentage));
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock >>> try { TimeUnit.SECONDS.sleep(1L); } catch (InterruptedException e) { e.printStackTrace(); }
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
			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			logParams.put("Thread Count : ", threadMXBean.getThreadCount());
			logParams.put("Current Thread Cpu Time : ", DurationFormatUtils.formatDurationWords(threadMXBean.getCurrentThreadCpuTime(), true, false));
			logParams.put("Current Thread User Time : ", DurationFormatUtils.formatDurationWords(threadMXBean.getCurrentThreadUserTime(), true, false));
			log.info(DEFAULT.value(), () -> logParams);
			
			logParams.clear();
			for (Long threadId : threadMXBean.getAllThreadIds()) {
				JSONObject logDetail = new JSONObject();
				ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
				if (threadInfo == null) { continue; }
				logDetail.put("Thread State : ", threadInfo.getThreadState());
				logDetail.put("Thread Cpu Time : ", DurationFormatUtils.formatDurationWords(threadMXBean.getThreadCpuTime(threadId), true, false));
				logParams.put(threadInfo.getThreadName(), logDetail);
			}
			log.info(DEFAULT.value(), () -> logParams);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// test for async lock >>> try { TimeUnit.SECONDS.sleep(1L); } catch (InterruptedException e) { e.printStackTrace(); }
			threadFairLock.unlock();
			log.debug(DEFAULT.value(), () -> UNLOCK.value());
		}
	}
	
}
