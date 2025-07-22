package com.flipfit.client;

import com.flipfit.business.*;

public class BookingClient {
	public static void main(String[] args ) {
		BookingBusiness booking = new BookingBusiness();
		booking.deleteBooking(null);
	}
}