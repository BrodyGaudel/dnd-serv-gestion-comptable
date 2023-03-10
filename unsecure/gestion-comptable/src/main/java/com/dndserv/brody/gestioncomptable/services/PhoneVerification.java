package com.dndserv.brody.gestioncomptable.services;

import com.dndserv.brody.gestioncomptable.entities.Verification;

public interface PhoneVerification {
    Verification verifyPhoneNumber(String phone);
    Boolean checkIfPhoneIsVerified(String code, String phone);
    void deleteVerification(String code);
}
