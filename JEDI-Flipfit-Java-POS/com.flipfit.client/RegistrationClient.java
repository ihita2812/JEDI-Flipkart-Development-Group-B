package com.flipflit.client;

import com.flipfit.business.RegistrationBusiness;

public class RegistrationClient {
	public static void main(String[] args ) {
		RegistrationBusiness registration = new RegistrationBusiness();
		registration.verifyUser(null);
	}
}