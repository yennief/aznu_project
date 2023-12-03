package org.apache.camel.example.springboot.model;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BookingIdentifierService {
	
	public String getBookingIdentifier() {
		return UUID.randomUUID().toString();
	}
	

}
