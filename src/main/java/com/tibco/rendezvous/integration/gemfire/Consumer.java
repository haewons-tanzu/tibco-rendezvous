package com.tibco.rendezvous.integration.gemfire;

import java.util.concurrent.BlockingQueue;

import org.apache.geode.cache.Region;

import com.tibco.tibrv.TibrvMsg;

public class Consumer implements Runnable {
	
	BlockingQueue<TibrvMsg> queue;
	private Region region;
	
	public Consumer(BlockingQueue<TibrvMsg> queue) {
		this.queue = queue;
	}

	public void run() {
		while (true) {
			try {
				TibrvMsg msg = queue.take();
				String key = msg.getSendSubject();
				String value = msg.toString();
				region.put(key, value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
