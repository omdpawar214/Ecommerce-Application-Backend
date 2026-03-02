package com.ecommerce.Ecommerce_App.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@Entity
@Data
@Table(name = "payment_details")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Order order;

    @NotBlank
    private String paymentMethod;

    //pg = payment Gateway
    private String pgPaymentId;
    private String pgStatus;
    private String pgName;
    private String pgResponseMessage;

    public Payment( Long paymentId, String pgPaymentId,String pgStatus, String pgName, String pgResponseMessage) {
        this.paymentId = paymentId;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgName = pgName;
        this.pgResponseMessage = pgResponseMessage;
    }
}
