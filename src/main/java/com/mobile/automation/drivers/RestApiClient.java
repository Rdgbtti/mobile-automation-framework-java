package com.mobile.automation.drivers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.mobile.automation.utils.ConfigReader;
import com.mobile.automation.utils.LoggerUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * RestApiClient - Cliente REST para testes de API (Framework HÍBRIDO)
 * Gerencia requisições HTTP (GET, POST, PUT, DELETE, PATCH)
 * Integrado com RestAssured
 */
public class RestApiClient {
    private static final String BASE_URL = ConfigReader.getApiBaseUrl();
    private RequestSpecification requestSpec;
    private Response lastResponse;
    private Map<String, String> headers;

    /**
     * Construtor - Inicializa RestAssured com URL base
     */
    public RestApiClient() {
        this.headers = new HashMap<>();
        this.requestSpec = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        LoggerUtil.info("RestApiClient inicializado com URL: " + BASE_URL);
    }

    /**
     * Adiciona header personalizado
     */
    public RestApiClient addHeader(String key, String value) {
        LoggerUtil.debug("Adicionando header: " + key + " = " + value);
        headers.put(key, value);
        requestSpec.header(key, value);
        return this;
    }

    /**
     * Adiciona token de autenticação (Bearer)
     */
    public RestApiClient addAuthToken(String token) {
        LoggerUtil.debug("Adicionando Bearer token");
        return addHeader("Authorization", "Bearer " + token);
    }

    /**
     * Adiciona token Basic Auth
     */
    public RestApiClient addBasicAuth(String username, String password) {
        LoggerUtil.info("Adicionando Basic Auth para: " + username);
        requestSpec.auth().basic(username, password);
        return this;
    }

    /**
     * Executa GET request
     */
    public Response get(String endpoint) {
        LoggerUtil.info("GET request: " + endpoint);
        try {
            lastResponse = requestSpec.get(endpoint);
            LoggerUtil.passStep("GET " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em GET request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em GET request", e);
        }
    }

    /**
     * Executa POST request
     */
    public Response post(String endpoint, Object body) {
        LoggerUtil.info("POST request: " + endpoint);
        try {
            lastResponse = requestSpec.body(body).post(endpoint);
            LoggerUtil.passStep("POST " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em POST request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em POST request", e);
        }
    }

    /**
     * Executa POST com JSON string
     */
    public Response postJson(String endpoint, String jsonBody) {
        LoggerUtil.info("POST request (JSON): " + endpoint);
        try {
            lastResponse = requestSpec.body(jsonBody).post(endpoint);
            LoggerUtil.passStep("POST " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em POST JSON request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em POST JSON request", e);
        }
    }

    /**
     * Executa PUT request
     */
    public Response put(String endpoint, Object body) {
        LoggerUtil.info("PUT request: " + endpoint);
        try {
            lastResponse = requestSpec.body(body).put(endpoint);
            LoggerUtil.passStep("PUT " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em PUT request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em PUT request", e);
        }
    }

    /**
     * Executa PATCH request
     */
    public Response patch(String endpoint, Object body) {
        LoggerUtil.info("PATCH request: " + endpoint);
        try {
            lastResponse = requestSpec.body(body).patch(endpoint);
            LoggerUtil.passStep("PATCH " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em PATCH request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em PATCH request", e);
        }
    }

    /**
     * Executa DELETE request
     */
    public Response delete(String endpoint) {
        LoggerUtil.info("DELETE request: " + endpoint);
        try {
            lastResponse = requestSpec.delete(endpoint);
            LoggerUtil.passStep("DELETE " + endpoint + " - Status: " + lastResponse.getStatusCode());
            return lastResponse;
        } catch (Exception e) {
            LoggerUtil.error("Erro em DELETE request: " + e.getMessage(), e);
            throw new RuntimeException("Falha em DELETE request", e);
        }
    }

    /**
     * Obtém última resposta
     */
    public Response getLastResponse() {
        return lastResponse;
    }

    /**
     * Obtém status code da última resposta
     */
    public int getStatusCode() {
        return lastResponse != null ? lastResponse.getStatusCode() : -1;
    }

    /**
     * Obtém body da resposta como String
     */
    public String getResponseBody() {
        return lastResponse != null ? lastResponse.getBody().asString() : "";
    }

    /**
     * Obtém body como JSON (JsonPath)
     */
    public <T> T getResponseBodyAs(Class<T> clazz) {
        if (lastResponse == null) {
            throw new RuntimeException("Nenhuma resposta disponível");
        }
        return lastResponse.getBody().as(clazz);
    }

    /**
     * Obtém header da resposta
     */
    public String getResponseHeader(String headerName) {
        return lastResponse != null ? lastResponse.getHeader(headerName) : "";
    }

    /**
     * Verifica se status code é de sucesso (2xx)
     */
    public boolean isSuccessful() {
        return lastResponse != null && lastResponse.getStatusCode() >= 200 && lastResponse.getStatusCode() < 300;
    }

    /**
     * Verifica status code específico
     */
    public boolean hasStatusCode(int expectedStatus) {
        return getStatusCode() == expectedStatus;
    }

    /**
     * Adiciona query parameter
     */
    public RestApiClient addQueryParam(String key, Object value) {
        LoggerUtil.debug("Adicionando query param: " + key + " = " + value);
        requestSpec.queryParam(key, value);
        return this;
    }

    /**
     * Reseta headers (para novo request)
     */
    public void reset() {
        LoggerUtil.debug("Resetando RestApiClient");
        this.headers.clear();
        this.requestSpec = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    /**
     * Obtém tempo de resposta (ms)
     */
    public long getResponseTime() {
        return lastResponse != null ? lastResponse.getTime() : 0;
    }

    /**
     * Verifica se response contém texto
     */
    public boolean responseContains(String text) {
        String body = getResponseBody();
        return body.contains(text);
    }
}

