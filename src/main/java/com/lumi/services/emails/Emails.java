package com.lumi.services.emails;

import com.lumi.core.net.HttpClient;
import com.lumi.services.emails.model.CreateEmailOptions;
import com.lumi.services.emails.model.CreateEmailResponse;
import com.lumi.services.emails.model.Email;
import java.util.HashMap;
import java.util.Map;

public class Emails {
    private final HttpClient httpClient;

    public Emails(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CreateEmailResponse send(CreateEmailOptions options) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("from", options.getFrom());
        if (options.getTo() != null && !options.getTo().isEmpty()) {
            if (options.getTo().size() == 1) {
                payload.put("to", options.getTo().get(0));
            } else {
                payload.put("to", options.getTo());
            }
        }
        payload.put("subject", options.getSubject());
        if (options.getHtml() != null) payload.put("html", options.getHtml());
        if (options.getText() != null) payload.put("text", options.getText());
        if (options.getReplyTo() != null) payload.put("reply_to", options.getReplyTo());
        if (options.getCc() != null && !options.getCc().isEmpty()) payload.put("cc", options.getCc());
        if (options.getBcc() != null && !options.getBcc().isEmpty()) payload.put("bcc", options.getBcc());
        if (options.getHeaders() != null && !options.getHeaders().isEmpty()) payload.put("headers", options.getHeaders());
        if (options.getAttachments() != null && !options.getAttachments().isEmpty()) payload.put("attachments", options.getAttachments());
        if (options.getTags() != null && !options.getTags().isEmpty()) payload.put("tags", options.getTags());
        if (options.getScheduledAt() != null) payload.put("scheduled_at", options.getScheduledAt());
        return httpClient.post("/emails", payload, CreateEmailResponse.class);
    }

    public Email get(String emailId) {
        return httpClient.get("/emails/" + emailId, Email.class);
    }
}
