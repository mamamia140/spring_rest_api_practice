keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365 -storepass changeit -keypass changeit -dname "CN=rest-practice.com, OU=BILGEM, O=TUBITAK, L=KOCAELI, S=MARMARA, C=TR"

keytool -exportcert -alias mykey -file mycert.cer -keystore keystore.p12 -storetype PKCS12 -storepass changeit

keytool -importcert -alias mykey -file mycert.cer -storetype PKCS12 -keystore truststore.p12 -storepass changeit -noprompt

