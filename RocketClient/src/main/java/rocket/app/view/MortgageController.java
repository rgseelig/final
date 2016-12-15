package rocket.app.view;
//
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Textbox;

import eNums.eAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import rocket.app.MainApp;
import rocketBase.RateDAL;
import rocketCode.Action;
import rocketData.LoanRequest;
import rocketDomain.RateDomainModel;

public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	@FXML
	private Textbox txtIncome;
	@FXML
	private Textbox txtExpenses;
	@FXML
	private Textbox txtCreditScore;
	@FXML
	private Textbox txtHouseCost;
	@FXML
	private ComboBox cmbterm;
	@FXML
	private Label incomelabel;
	@FXML
	private Label expenselabel;
	@FXML
	private Label creditscorelabel;
	@FXML
	private Label housecostlabel; 
	@FXML
	private Button button;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Button button = (Button) event.getSource();
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		a.setLoanRequest(lq);
		
		// Overly-complicated way to get the set the Rate of the Loan Request
		ArrayList<RateDomainModel> x1 = RateDAL.getAllRates();
		for (int i =0; i>x1.size(); i++){
			if (x1.get(i).getiMinCreditScore() == Integer.parseInt(txtCreditScore.toString())){
				lq.setdRate(x1.get(i).getdInterestRate());
			}
		}
		//Set the Income
		lq.setIncome(Integer.parseInt(txtIncome.toString()));;
		
		//Set the term
		lq.setiTerm((int) cmbterm.getValue());
		
		//Set the Amount 
		lq.setdAmount(Integer.parseInt(txtHouseCost.toString()));
		
		//Set the Credit Score
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.toString()));
		
		//Set payments
		lq.setdPayment(Integer.parseInt(txtExpenses.toString()));
		
		//not sure what number to use for down payment. ~20% is average apparently
		lq.setiDownPayment((int) .2 * Integer.parseInt(txtHouseCost.toString()));
		
		//set Expenses
		lq.setExpenses((double)Integer.parseInt(txtExpenses.toString()));
		
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
		
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		double PITI1 = lRequest.getIncome()*(.28*lRequest.getdAmount());
		double PITI2 = (lRequest.getIncome()*(.38*lRequest.getdAmount())) - lRequest.getExpenses();
		double piti = 0.00;
		
		if (PITI1 >= PITI2){
			piti = PITI2;
		}else{
			piti = PITI1;
		}
		
		
		label1.setText("Down payment is:  " + lRequest.getiDownPayment());
		label2.setText("Your maximum monthly payment is:   " +piti);
	}
}
