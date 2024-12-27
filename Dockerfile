# Ubuntu tabanlı bir temel imaj kullanıyoruz
FROM ubuntu:20.04

# Java 21 ve apt-get komutları ile gerekli paketleri kuruyoruz
RUN apt-get update -y && apt-get install -y openjdk-21-jdk

# Build sırasında oluşturulan JAR dosyasını /home/ecommerce dizinine kopyalıyoruz
COPY target/e-commerce-0.0.1-SNAPSHOT.jar /home/ecommerce/application.jar

# Java uygulamasını çalıştıracak komut
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/home/ecommerce/application.jar"]

EXPOSE 8080
