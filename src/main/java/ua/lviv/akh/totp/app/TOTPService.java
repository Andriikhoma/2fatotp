package ua.lviv.akh.totp.app;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.stereotype.Service;

@Service
public class TOTPService {

    public String generateSecretKey() {
        return Base32.random();
    }

    public String getTOTPCode(String secretKey) {
        Totp totp = new Totp(secretKey);
        return totp.now();
    }

    public boolean verifyCode(String userCode, String secretKey) {
        Totp totp = new Totp(secretKey);
        return totp.verify(userCode);
    }
}