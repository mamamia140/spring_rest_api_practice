keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365 -storepass changeit -keypass changeit -dname "CN=rest-practice.com, OU=BILGEM, O=TUBITAK, L=KOCAELI, S=MARMARA, C=TR"

keytool -exportcert -alias mykey -file mycert.cer -keystore keystore.p12 -storetype PKCS12 -storepass changeit

keytool -importcert -alias mykey -file mycert.cer -storetype PKCS12 -keystore truststore.p12 -storepass changeit -noprompt

C:\visualstudio_workspace\java_projects\spring_rest_api_practice\src\main\resources\certificates>openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout localhost.key -out localhost.crt -config cert.conf
Generating a RSA private key
................+++++
....................................................+++++
writing new private key to 'localhost.key'
-----

C:\visualstudio_workspace\java_projects\spring_rest_api_practice\src\main\resources\certificates>openssl pkcs12 -export -in localhost.crt -inkey localhost.key -name localhost -out keystore.p12 -passout pass:changeit

C:\visualstudio_workspace\java_projects\spring_rest_api_practice\src\main\resources\certificates>keytool -importcert -alias localhost -file localhost.crt -keystore truststore.p12 -storetype PKCS12 -storepass changeit -noprompt
Certificate was added to keystore

C:\visualstudio_workspace\java_projects\spring_rest_api_practice\src\main\resources\certificates>


