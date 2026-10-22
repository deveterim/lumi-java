# Lumi Java SDK (`lumi-java`)

> SDK oficial em Java para a plataforma de infraestrutura de e-mails corporativos e transacionais **Lumi**, com interface ergonómica e suporte moderno a Java 11+.

---

## 📦 Instalação

### Maven (`pom.xml`)
```xml
<dependency>
    <groupId>com.lumi</groupId>
    <artifactId>lumi-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle (`build.gradle`)
```groovy
implementation 'com.lumi:lumi-java:1.0.0'
```

---

## 🚀 Como Usar

### 1. Enviar E-mail Transacional

```java
import com.lumi.Lumi;
import com.lumi.services.emails.model.CreateEmailOptions;
import com.lumi.services.emails.model.CreateEmailResponse;

public class App {
    public static void main(String[] args) {
        Lumi lumi = new Lumi("lu_live_123456789");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@suaempresa.ao")
                .to("destinatario@exemplo.com")
                .subject("Boas-vindas ao Lumi!")
                .html("<h1>Conta Ativada</h1><p>Seu acesso foi liberado com sucesso.</p>")
                .build();

        CreateEmailResponse response = lumi.emails().send(params);
        System.out.println("E-mail enviado! ID: " + response.getId());
    }
}
```

### 2. Envio em Lote (Batch)

```java
List<CreateEmailOptions> batch = List.of(
    CreateEmailOptions.builder().from("news@suaempresa.ao").to("user1@gmail.com").subject("Novidade").html("<p>Oi 1</p>").build(),
    CreateEmailOptions.builder().from("news@suaempresa.ao").to("user2@gmail.com").subject("Novidade").html("<p>Oi 2</p>").build()
);

CreateBatchEmailsResponse resp = lumi.batch().send(batch);
```

### 3. Gestão de Domínios e API Keys

```java
Domain domain = lumi.domains().create("suaempresa.ao");
Domain verified = lumi.domains().verify(domain.getId());

List<ApiKey> keys = lumi.apiKeys().list();
```

---

## 📄 Licença

MIT © [Lumi](https://lumi.email)
