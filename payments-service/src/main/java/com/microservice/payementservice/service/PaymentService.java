package com.microservice.payementservice.service;

import com.microservice.payementservice.payload.PaymentRequest;
import com.microservice.payementservice.payload.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
