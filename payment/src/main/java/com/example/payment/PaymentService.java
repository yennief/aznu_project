package com.example.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import com.example.payment.model.PaymentException;
import com.example.payment.model.PaymentRequest;
import com.example.payment.model.PaymentResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@org.springframework.web.bind.annotation.RestController
@OpenAPIDefinition(info = @Info(title = "Table factory service", version = "1", description = "Service for booking train ticket"))

public class PaymentService {

	@org.springframework.web.bind.annotation.PostMapping("/payment")
	@Operation(summary = "paymentoperation", description = "operation for payment", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) })
	})
	public PaymentResponse payment(
			@org.springframework.web.bind.annotation.RequestBody PaymentRequest paymentRequest) {

		if (paymentRequest != null && paymentRequest.getAmount() != null
				&& paymentRequest.getAmount().getValue() != null
				&& paymentRequest.getAmount().getValue().compareTo(new BigDecimal(0)) < 0) {

			throw new PaymentException("Amount value can not negative");
		}
		PaymentResponse paymentResponse = new PaymentResponse();
		Random random = new Random();
		int min = 1;
		int randomInt = random.nextInt(Integer.MAX_VALUE - min) + min;
		paymentResponse.setTransactionDate(new Date());
		paymentResponse.setTransactionId(randomInt);
		return paymentResponse;
	}

	@Operation(summary = "Get payment", description = "Get payment", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) })
	})
	@org.springframework.web.bind.annotation.GetMapping("/payment/{id}")
	public PaymentResponse getPayment(@PathVariable int id) throws PaymentException {
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setTransactionDate(new Date());
		paymentResponse.setTransactionId(id);

		return paymentResponse;
	}

}
