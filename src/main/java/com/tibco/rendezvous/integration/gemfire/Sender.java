package com.tibco.rendezvous.integration.gemfire;

import java.util.Date;

import org.apache.geode.cache.Region;

import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;

public class Sender implements TibrvMsgCallback {
	
	private Region region;
	public Sender(Region region) {
		this.region = region;
	}

	public void onMsg(TibrvListener listener, TibrvMsg msg) {
		System.out.println((new Date()).toString() + ": subject=" + msg.getSendSubject() + ", reply="
				+ msg.getReplySubject() + ", message=" + msg.toString());

		System.out.flush();
		
		String key = msg.getSendSubject();
		String value = msg.toString();
		region.put(key, value);
	}
}