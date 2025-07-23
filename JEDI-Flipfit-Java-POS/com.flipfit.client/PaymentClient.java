package com.flipflit.client;

import com.flipfit.business.PaymentBusiness;

public class PaymentClient {
	public static void main(String[] args ) {
		PaymentBusiness payment = new PaymentBusiness();
		payment.makePayment(null);
	}
}