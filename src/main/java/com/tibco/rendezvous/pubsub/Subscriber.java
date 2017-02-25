package com.tibco.rendezvous.pubsub;

import java.util.Date;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvCmListener;
import com.tibco.tibrv.TibrvCmMsg;
import com.tibco.tibrv.TibrvCmQueueTransport;
import com.tibco.tibrv.TibrvDispatcher;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvQueue;
import com.tibco.tibrv.TibrvRvdTransport;

public class Subscriber implements TibrvMsgCallback {
	String service = "7500";
	String network = "loopback";
	String daemon = "tcp:9025";
	String subject = "SOME.SUBJECT";
	String queueName = "cm.queue";
	
	public Subscriber() {
	}

	public static void main(String[] args) throws TibrvException, InterruptedException {
		Subscriber subscriber = new Subscriber();
		subscriber.receive();
	}
	
	public void receive() throws TibrvException, InterruptedException {
		//Tibrv.open(Tibrv.IMPL_NATIVE);
		Tibrv.open(Tibrv.IMPL_JAVA);
		TibrvRvdTransport transport = new TibrvRvdTransport(service, network, daemon);
		TibrvQueue queue = new TibrvQueue();
		TibrvCmQueueTransport cmqTransport = new TibrvCmQueueTransport(transport, queueName);
		TibrvCmListener queueListener = new TibrvCmListener(queue, this, cmqTransport, subject, null);
		
		TibrvDispatcher disp = new TibrvDispatcher(queue);
		System.out.println("Queue name" + queueName + ", listening on subject " + subject);
		disp.join();
		/*
		new TibrvListener(Tibrv.defaultQueue(), new Subscriber(), transport, subject, null);
		while (true) {
			try {
				Tibrv.defaultQueue().dispatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
	}

	public void onMsg(TibrvListener listener, TibrvMsg msg) {
		try {
			long seqno = TibrvCmMsg.getSequence(msg);
			System.out.println("Received message with seqno=" + seqno + ": " + msg);
			System.out.flush();
		} catch (TibrvException e) {
			e.printStackTrace();
		}
	}
}