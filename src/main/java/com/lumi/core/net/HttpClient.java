package com.lumi.core.net;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumi.core.exception.LumiException;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClient {
    private final String apiKey;
    private final String baseUrl;
    private final java.net.http.HttpClient httpClient;
    private final ObjectMapper mapper;

    public HttpClient(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = (baseUrl != null ? baseUrl : "https://api.lumi.com/v1").replaceAll("/$", "");
        this.httpClient = java.net.http.HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T post(String path, Object payload, Class<T> responseType) {
        try {
            String json = mapper.writeValueAsString(payload);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + (path.startsWith("/") ? path : "/" + path)))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "lumi-java/1.0.3")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new LumiException("Lumi API error (" + response.statusCode() + "): " + response.body(), response.statusCode());
            }

            if (responseType == Void.class || response.body() == null || response.body().isBlank()) {
                return null;
            }

            JsonNode root = mapper.readTree(response.body());
            if (root.has("data") && !responseType.getName().endsWith("ListResponse")) {
                try {
                    return mapper.treeToValue(root.get("data"), responseType);
                } catch (Exception ignored) {}
            }
            return mapper.treeToValue(root, responseType);
        } catch (LumiException e) {
            throw e;
        } catch (Exception e) {
            throw new LumiException("Failed to perform HTTP POST request: " + e.getMessage(), e);
        }
    }

    public <T> T get(String path, Class<T> responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + (path.startsWith("/") ? path : "/" + path)))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "lumi-java/1.0.3")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new LumiException("Lumi API error (" + response.statusCode() + "): " + response.body(), response.statusCode());
            }

            if (responseType == Void.class || response.body() == null || response.body().isBlank()) {
                return null;
            }

            JsonNode root = mapper.readTree(response.body());
            if (root.has("data") && !responseType.getName().endsWith("ListResponse")) {
                try {
                    return mapper.treeToValue(root.get("data"), responseType);
                } catch (Exception ignored) {}
            }
            return mapper.treeToValue(root, responseType);
        } catch (LumiException e) {
            throw e;
        } catch (Exception e) {
            throw new LumiException("Failed to perform HTTP GET request: " + e.getMessage(), e);
        }
    }

    public void delete(String path) {
        delete(path, Void.class);
    }

    public <T> T delete(String path, Class<T> responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + (path.startsWith("/") ? path : "/" + path)))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "lumi-java/1.0.3")
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new LumiException("Lumi API error (" + response.statusCode() + "): " + response.body(), response.statusCode());
            }

            if (responseType == Void.class || response.body() == null || response.body().isBlank()) {
                return null;
            }

            JsonNode root = mapper.readTree(response.body());
            if (root.has("data") && !responseType.getName().endsWith("ListResponse")) {
                try {
                    return mapper.treeToValue(root.get("data"), responseType);
                } catch (Exception ignored) {}
            }
            return mapper.treeToValue(root, responseType);
        } catch (LumiException e) {
            throw e;
        } catch (Exception e) {
            throw new LumiException("Failed to perform HTTP DELETE request: " + e.getMessage(), e);
        }
    }
}
