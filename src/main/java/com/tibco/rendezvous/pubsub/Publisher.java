package com.tibco.rendezvous.pubsub;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

// https://gist.github.com/dennysfredericci
public class Publisher {

	public Publisher() {
	}

	public static void main(String[] args) throws Exception {

		String service = "7500";
		String network = "loopback";
		String daemon = "tcp:9025";
		String subject = "SOME.SUBJECT";

		//Tibrv.open(Tibrv.IMPL_NATIVE);
		Tibrv.open(Tibrv.IMPL_JAVA);
		TibrvRvdTransport transport = new TibrvRvdTransport(service, network, daemon);

		TibrvMsg msg = new TibrvMsg();
		msg.setSendSubject(subject);
		msg.update("FIELD", "ASDF");
		transport.send(msg);
	}
}
