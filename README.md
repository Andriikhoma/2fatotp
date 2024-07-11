Using the Endpoints
Generate a Secret Key:

curl -X GET http://localhost:8080/generate-key
Generate QR Code:

curl -X GET "http://localhost:8080/generate-qr-code/JBSWY3DPEHPK3PXP?userAccount=user@example.com&issuer=MyApp" --output qrcode.png

Scan QR Code:
Open Google Authenticator and scan the qrcode.png.

Verify TOTP Code:

curl -X POST http://localhost:8080/verify-code \
-H "Content-Type: application/x-www-form-urlencoded" \
-d "userCode=123456&secretKey=JBSWY3DPEHPK3PXP"
Replace 123456 with the code from Google Authenticator.

This setup ensures that users can securely use Google Authenticator for 2FA in your Spring Boot application.