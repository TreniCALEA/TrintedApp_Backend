package it.unical.inf.ea.trintedapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment-api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PaymentController {
    private static Gson gson = new Gson();

    static class CreatePayment {
        @SerializedName("items")
        Object[] items;

        public Object[] getItems(){
            return items;
        }
    }

    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret){
            this.clientSecret = clientSecret;
        }
    }

    static Long calculateOrderAmount(Object[] items){
        throw new UnsupportedOperationException("");
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<CreatePaymentResponse> checkout(@RequestBody CreatePayment payment) throws StripeException {   
        Stripe.apiKey = "sk_test_9W1R4v0cz6AtC9PVwHFzywti";
        
        CreatePayment postBody = payment;

        PaymentIntentCreateParams params = 
            PaymentIntentCreateParams.builder()
            .setAmount(calculateOrderAmount(postBody.getItems()))
            .setCurrency("usd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods
                    .builder()
                    .setEnabled(true)
                    .build()
            ).build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());

        return ResponseEntity.ok(paymentResponse);
    }
}
