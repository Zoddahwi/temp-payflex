package com.fintechedge.payflex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "paymentRequest")
public class PaymentRequest {
    private String accountNumber;
    private String destinationBank;
    private String amount;
    private String destinationTelco;
    private String mobileNumber;
    private String transactionRef;
    private String transactionId;
    private String userId;
    private String status;
}
