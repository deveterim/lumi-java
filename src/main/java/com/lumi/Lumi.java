package com.lumi;

import com.lumi.core.net.HttpClient;
import com.lumi.services.apikeys.ApiKeys;
import com.lumi.services.batch.Batch;
import com.lumi.services.domains.Domains;
import com.lumi.services.emails.Emails;

public class Lumi {
    private final HttpClient httpClient;
    private final Emails emails;
    private final Batch batch;
    private final Domains domains;
    private final ApiKeys apiKeys;

    public Lumi() {
        this(System.getenv("MAILIFICA_API_KEY"));
    }

    public Lumi(String apiKey) {
        this(apiKey, System.getenv("MAILIFICA_BASE_URL") != null ? System.getenv("MAILIFICA_BASE_URL") : "https://api.lumi.com/v1");
    }

    public Lumi(String apiKey, String baseUrl) {
        this.httpClient = new HttpClient(apiKey, baseUrl);
        this.emails = new Emails(this.httpClient);
        this.batch = new Batch(this.httpClient);
        this.domains = new Domains(this.httpClient);
        this.apiKeys = new ApiKeys(this.httpClient);
    }

    public Emails emails() {
        return this.emails;
    }

    public Batch batch() {
        return this.batch;
    }

    public Domains domains() {
        return this.domains;
    }

    public ApiKeys apiKeys() {
        return this.apiKeys;
    }
}
