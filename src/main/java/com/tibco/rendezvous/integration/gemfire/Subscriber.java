package com.tibco.rendezvous.integration.gemfire;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

public class Subscriber {
	private LinkedBlockingQueue<TibrvMsg> queue;
	
	public Subscriber() {
	}

	public static void main(String[] args) throws TibrvException {
		String service = "7500";
		String network = "loopback";
		String daemon = "tcp:9025";
		String subject = "SOME.SUBJECT";
		
		int threadCount = Integer.parseInt(args[0]);
		int queueSize = Integer.parseInt(args[1]);

		//Tibrv.open(Tibrv.IMPL_NATIVE);
		Tibrv.open(Tibrv.IMPL_JAVA);
		TibrvRvdTransport transport = new TibrvRvdTransport(service, network, daemon);
		
		
		ClientCache cache = new ClientCacheFactory()
				.addPoolLocator("localhost", 10334)
				.create();
		
		Region<String, byte[]> region = cache.<String, byte[]>createClientRegionFactory(ClientRegionShortcut.PROXY).create("EQUIPMENT");
		
		new TibrvListener(Tibrv.defaultQueue(), new Sender(threadCount, queueSize, region), transport, subject, null);

		while (true) {
			try {
				Tibrv.defaultQueue().dispatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
}