package com.dndserv.brody.gestioncomptable.services.implementations;

import com.dndserv.brody.gestioncomptable.entities.Verification;
import com.dndserv.brody.gestioncomptable.repositories.VerificationRepository;
import com.dndserv.brody.gestioncomptable.services.PhoneVerification;

import java.util.UUID;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PhoneVerificationImpl implements PhoneVerification {

    private static final String ACCOUNT_SID = "";
    private static final String AUTH_TOKEN = "";
    private static final String TWILIO_NUMBER = "";

    private final VerificationRepository verificationRepository;

    public PhoneVerificationImpl(VerificationRepository verificationRepository) {
        this.verificationRepository = verificationRepository;
    }

    @Override
    public Verification verifyPhoneNumber(String phone) {
        String code = sendCodeToPhone(phone);
        return verificationRepository.save(new Verification(code, phone));
    }

    @Override
    public Boolean checkIfPhoneIsVerified(String code, String phone) {
        Verification verification = verificationRepository.findById(code).orElse(null);
        return verification != null && verification.getPhone().equals(phone);
    }

    @Override
    public void deleteVerification(String code) {
        verificationRepository.deleteById(code);
    }

    private String sendCodeToPhone(String phone){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String code = UUID.randomUUID().toString();
        Message message = Message
                .creator(
                        new PhoneNumber(phone),
                        new PhoneNumber(TWILIO_NUMBER),
                        code
                ).create();
        log.info(message.toString());
        return code;
    }

}
