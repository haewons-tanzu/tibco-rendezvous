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
		
		String SEPARATOR = "|";

		Tibrv.open(Tibrv.IMPL_NATIVE);
		//Tibrv.open(Tibrv.IMPL_JAVA);
		TibrvRvdTransport transport = new TibrvRvdTransport(service, network, daemon);

		TibrvMsg msg = new TibrvMsg();
		msg.setSendSubject(subject);
		
		StringBuffer key = new StringBuffer();
		key.append("eqpIndex" + SEPARATOR);
		key.append("unitIndex" + SEPARATOR);
		key.append("paramIndex" + SEPARATOR);
		key.append("lotId" + SEPARATOR);
		key.append("ppId" + SEPARATOR);
		key.append("recipeId" + SEPARATOR);
		key.append("stepSeq" + SEPARATOR);
		key.append("pairId" + SEPARATOR);
		key.append("processId" + SEPARATOR);
		key.append("waferId" + SEPARATOR);
		key.append("waferNo" + SEPARATOR);
		key.append("lotType" + SEPARATOR);
		key.append("statusTf");
		//key.append("seq");
		
		StringBuffer value = new StringBuffer();
		value.append("eqpIndex" + SEPARATOR);
		value.append("unitIndex" + SEPARATOR);
		value.append("paramIndex" + SEPARATOR);
		value.append("lotId" + SEPARATOR);
		value.append("ppId" + SEPARATOR);
		value.append("recipeId" + SEPARATOR);
		value.append("stepSeq" + SEPARATOR);
		value.append("pairId" + SEPARATOR);
		value.append("processId" + SEPARATOR);
		value.append("waferId" + SEPARATOR);
		value.append("waferNo" + SEPARATOR);
		value.append("lotType" + SEPARATOR);
		value.append("statusTf" + SEPARATOR);
		value.append("ts" + SEPARATOR);
		value.append("vl" + SEPARATOR);
		value.append("ls" + SEPARATOR);
		value.append("us" + SEPARATOR);
		value.append("sl");
			
		while (true) {
			msg.update("KEY", key.toString());
			msg.update("VALUE", value.toString());
			transport.send(msg);
		}
	}
}
