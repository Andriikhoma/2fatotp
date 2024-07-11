package ua.lviv.akh.totp.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static byte[] generateQRCode(String secretKey, String userAccount, String issuer) throws IOException, WriterException {
        String otpAuthURL = getOtpAuthURL(issuer, userAccount, secretKey);
        return createQRCode(otpAuthURL, 200, 200);
    }

    private static String getOtpAuthURL(String issuer, String userAccount, String secretKey) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer, userAccount, secretKey, issuer);
    }

    private static byte[] createQRCode(String barcodeText, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = qrCodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}