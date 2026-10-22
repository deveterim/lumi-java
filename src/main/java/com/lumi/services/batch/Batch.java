package com.lumi.services.batch;

import com.lumi.core.net.HttpClient;
import com.lumi.services.batch.model.CreateBatchEmailsResponse;
import com.lumi.services.emails.model.CreateEmailOptions;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Batch {
    private final HttpClient httpClient;

    public Batch(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CreateBatchEmailsResponse send(List<CreateEmailOptions> options) {
        List<Map<String, Object>> items = new java.util.ArrayList<>();
        for (CreateEmailOptions opt : options) {
            Map<String, Object> m = new java.util.HashMap<>();
            m.put("from", opt.getFrom());
            if (opt.getTo() != null && !opt.getTo().isEmpty()) {
                if (opt.getTo().size() == 1) {
                    m.put("to", opt.getTo().get(0));
                } else {
                    m.put("to", opt.getTo());
                }
            }
            m.put("subject", opt.getSubject());
            if (opt.getHtml() != null) m.put("html", opt.getHtml());
            if (opt.getText() != null) m.put("text", opt.getText());
            if (opt.getReplyTo() != null) m.put("reply_to", opt.getReplyTo());
            if (opt.getCc() != null && !opt.getCc().isEmpty()) m.put("cc", opt.getCc());
            if (opt.getBcc() != null && !opt.getBcc().isEmpty()) m.put("bcc", opt.getBcc());
            if (opt.getHeaders() != null && !opt.getHeaders().isEmpty()) m.put("headers", opt.getHeaders());
            if (opt.getAttachments() != null && !opt.getAttachments().isEmpty()) m.put("attachments", opt.getAttachments());
            if (opt.getTags() != null && !opt.getTags().isEmpty()) m.put("tags", opt.getTags());
            if (opt.getScheduledAt() != null) m.put("scheduled_at", opt.getScheduledAt());
            items.add(m);
        }
        Map<String, Object> payload = Collections.singletonMap("emails", items);
        return httpClient.post("/emails/batch", payload, CreateBatchEmailsResponse.class);
    }
}
