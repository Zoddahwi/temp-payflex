package com.fintechedge.payflex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "paymentResponse")
public class PaymentResponse {
    private String status; // This is the status of the transaction
    private String message; // This is the message from the bank
    private PaymentRequest paymentRequest; // This is the payment request object
    private String transactionId; // This is the transaction ID from the bank
    private String transactionRef;  // This is the transaction reference from the bank
}
