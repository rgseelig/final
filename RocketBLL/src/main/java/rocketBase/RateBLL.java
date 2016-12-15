package rocketBase;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.ss.formula.functions.*;

import rocketDomain.RateDomainModel;
//
public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) 
	{
		//TODO - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		
		//Sort the ArrayList
		for (int i =0; i < rates.size(); i++){
			if(rates.get(i).getdInterestRate() < rates.get(0).getdInterestRate()){
				rates.set(0, rates.get(i));
			}
		}
		/*
		 * Get and return Interest Rate of Credit Score
		 * Not sure why it had to be sorted.
		 */
		Double InterestRate = 0.00;
		for (int i =0; i>rates.size(); i++){
			if (rates.get(i).getiMinCreditScore() == GivenCreditScore){
				InterestRate = rates.get(i).getdInterestRate();
				break;
			}
		}
	
		return InterestRate;
	
		
		
		
	}
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		// looks ok?
		return FinanceLib.pmt(r, n, p, f, t);
		
	}
	}

