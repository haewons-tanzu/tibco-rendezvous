package com.tibco.rendezvous.integration.gemfire;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.geode.cache.Region;

import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;

public class Sender implements TibrvMsgCallback {
	BlockingQueue<TibrvMsg> queue;
	private Region region;
	
	// 625, 150000 
	public Sender(int threadCount, int queueSize, Region region) {
		queue = new LinkedBlockingQueue<TibrvMsg>(queueSize);
		this.region = region;
		
		for (int i = 0; i<threadCount; i++) {
			Thread thread = new Thread(new Consumer(queue));
			thread.start();
		}
	}

	public void onMsg(TibrvListener listener, TibrvMsg msg) {
		try {
			queue.add(msg);
		} catch (Exception e) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}