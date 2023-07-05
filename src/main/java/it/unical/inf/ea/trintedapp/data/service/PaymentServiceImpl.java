package it.unical.inf.ea.trintedapp.data.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;

public class PaymentServiceImpl implements PaymentService {    
    @Override
    public void checkOut() {
        Stripe.apiKey = "sk_test_9W1R4v0cz6AtC9PVwHFzywti";
    }
    
}
