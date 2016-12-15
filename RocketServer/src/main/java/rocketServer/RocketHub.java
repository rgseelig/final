package rocketServer;

import java.io.IOException;

import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;

//
public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}
	
	double rate;
	double n;
	double p;
	double f;
	boolean t;

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			//	TODO - RocketHub.messageReceived
			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			
			try{
				rate = RateBLL.getRate(lq.getiCreditScore());
			}catch (Exception e){
				System.out.println("error in rate look-up");
			}
			//update rate
			lq.setdRate(rate);
			//update payment
			lq.setdPayment(RateBLL.getPayment(rate, n, p, f, t));
		
			
			sendToAll(lq);
		}
	}
}
