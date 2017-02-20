package com.tibco.rendezvous.integration.gemfire;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvRvdTransport;

public class Subscriber {

	public Subscriber() {
	}

	public static void main(String[] args) throws TibrvException {
		String service = "7500";
		String network = "loopback";
		String daemon = "tcp:9025";
		String subject = "SOME.SUBJECT";

		//Tibrv.open(Tibrv.IMPL_NATIVE);
		Tibrv.open(Tibrv.IMPL_JAVA);
		TibrvRvdTransport transport = new TibrvRvdTransport(service, network, daemon);
		
		
		ClientCache cache = new ClientCacheFactory()
				.addPoolLocator("localhost", 10334)
				.create();
		
		Region<String, byte[]> region = cache.<String, byte[]>createClientRegionFactory(ClientRegionShortcut.PROXY).create("EQUIPMENT");
		
		new TibrvListener(Tibrv.defaultQueue(), new Sender(region), transport, subject, null);

		while (true) {
			try {
				Tibrv.defaultQueue().dispatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
}