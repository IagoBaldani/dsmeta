package com.devsuperior.dsmeta.service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Autowired
    private SaleRepository saleRepository;

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.from}")
    private String twilioPhoneFrom;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;

    public void sendSms(Long id) {

        Sale sale = saleRepository.findById(id).get();

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);

        StringBuilder bodyMsg = new StringBuilder();


        bodyMsg.append("Vendedor " + sale.getSellerName());
        String amountSaleFormated = String.format("R$%.2f", sale.getAmount());
        bodyMsg.append(" realizou uma venda de " + amountSaleFormated +"!");

        Message message = Message.creator(to, from, bodyMsg.toString()).create();

        System.out.println(message.getSid());
    }
}
