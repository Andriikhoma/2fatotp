package ua.lviv.akh.totp.app;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TOTPController {

    @Autowired
    private TOTPService totpService;

    @GetMapping(value = "/generate-key")
    public String generateKey() {
        return totpService.generateSecretKey();
    }

    @GetMapping(value = "/generate-qr-code/{secretKey}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String secretKey, @RequestParam String userAccount, @RequestParam String issuer) throws IOException, WriterException {
        byte[] qrCode = QRCodeGenerator.generateQRCode(secretKey, userAccount, issuer);
        return ResponseEntity.status(HttpStatus.OK).body(qrCode);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String userCode, @RequestParam String secretKey) {
        boolean isValid = totpService.verifyCode(userCode, secretKey);
        return ResponseEntity.status(HttpStatus.OK).body(isValid);
    }
}