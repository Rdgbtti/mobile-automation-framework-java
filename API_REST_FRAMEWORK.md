# 🔌 REST API Support - Framework TRIPLE HYBRID

## Visão Geral

O **mobile-automation-framework** agora é **TRIPLE HYBRID**, suportando:
- ✅ MOBILE (Appium/Android/iOS)
- ✅ WEB (Selenium/Chrome/Firefox/Edge/Safari)
- ✅ **API REST (REST Assured/HTTP)**

---

## 📦 Novas Classes Framework

### 1. RestApiClient.java
**Localização:** `src/main/java/com/mobile/automation/drivers/RestApiClient.java`

**Responsabilidade:** Gerenciar requisições HTTP (GET, POST, PUT, DELETE, PATCH)

**Dependência:** REST Assured 5.4.0

**Métodos principais:**
- `get(endpoint)` - GET request
- `post(endpoint, body)` - POST request
- `put(endpoint, body)` - PUT request
- `patch(endpoint, body)` - PATCH request
- `delete(endpoint)` - DELETE request
- `addHeader(key, value)` - Adicionar header customizado
- `addAuthToken(token)` - Adicionar Bearer token
- `addBasicAuth(user, pass)` - Adicionar Basic auth
- `addQueryParam(key, value)` - Adicionar query parameter
- `isSuccessful()` - Verificar se status é 2xx
- `getStatusCode()` - Obter status code
- `getResponseBody()` - Obter body como string
- `getResponseTime()` - Obter tempo de resposta

---

## 🔄 BaseTest Expandido

**Mudanças:**

```java
// Antes (Mobile + Web)
protected AppiumDriver driver;
protected WebDriver webDriver;

// Agora (Mobile + Web + API)
protected AppiumDriver driver;
protected WebDriver webDriver;
protected RestApiClient apiClient;  // NOVO
```

**Novo método:**
- `setUpApi()` - Inicializa RestApiClient com configs
- `isApiTest()` - Verifica se é teste API

**Detecta test.type:**
```
test.type=mobile  → setUpMobile()
test.type=web     → setUpWeb()
test.type=api     → setUpApi()  ← NOVO
```

---

## 🔐 TestContext Expandido

**Adição:**
```java
private RestApiClient apiClient;  // NOVO
public RestApiClient getApiClient()
public void setApiClient(RestApiClient apiClient)
public boolean isApiTest()
```

---

## ⚙️ ConfigReader Expandido

**Novos métodos:**
```java
getApiBaseUrl()          // URL da API
getApiVersion()          // Versão da API
isApiAuthRequired()      // Se precisa auth
getApiAuthToken()        // Bearer token
getApiAuthUsername()     // Username basic auth
getApiAuthPassword()     // Password basic auth
getApiTimeout()          // Timeout em segundos
isApiLoggingEnabled()    // Log habilitado
```

---

## 📦 Dependências Adicionadas

```xml
<!-- REST Assured -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
</dependency>

<!-- Gson (JSON parsing) -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

---

## 🚀 Como Usar

### Em seu projeto de testes:

**pom.xml:**
```xml
<dependency>
    <groupId>com.mobile.automation</groupId>
    <artifactId>mobile-automation-framework</artifactId>
    <version>2.0.0</version>  <!-- Triple Hybrid -->
</dependency>
```

**Para testes API:**
```java
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.drivers.RestApiClient;
import com.google.gson.JsonObject;
import io.restassured.response.Response;

public class MyApiTest extends BaseTest {
    @Test
    public void testGetUsers() {
        // apiClient é injetado automaticamente
        Response response = apiClient.get("/users");
        Assert.assertTrue(apiClient.isSuccessful());
    }
    
    @Test
    public void testCreateUser() {
        JsonObject payload = new JsonObject();
        payload.addProperty("name", "João");
        
        Response response = apiClient.post("/users", payload.toString());
        Assert.assertTrue(response.getStatusCode() == 201);
    }
}
```

---

## 📝 Configuração API

### config.properties

```properties
# Tipo de teste
test.type=api

# URL da API
api.base.url=http://localhost:8080/api
api.version=v1

# Autenticação (opcional)
api.auth.required=false
api.auth.type=bearer
api.auth.token=your_token
api.auth.username=admin
api.auth.password=password

# Performance
api.timeout=30
api.logging.enabled=true
```

---

## 🎯 Fluxo de Execução API

```
BaseTest.setUp()
    ↓
Detecta: test.type=api
    ↓
setUpApi()
    ↓
RestApiClient.constructor()
    ↓
Configurar BaseURI + Headers
    ↓
Se auth.required:
  ├─ bearer → addAuthToken()
  └─ basic  → addBasicAuth()
    ↓
Teste Executado
    ↓
apiClient.get/post/put/delete/patch()
    ↓
Response retornada
    ↓
BaseTest.tearDown()
    ↓
apiClient.reset()
```

---

## 🌐 HTTP Methods Suportados

| Método | Uso | Status Esperado |
|--------|-----|-----------------|
| **GET** | Recuperar dados | 200 OK |
| **POST** | Criar novo recurso | 201 Created |
| **PUT** | Atualizar completo | 200 OK |
| **PATCH** | Atualizar parcial | 200 OK |
| **DELETE** | Deletar recurso | 200/204 |

---

## 🔑 Autenticação Suportada

### Bearer Token
```java
apiClient.addAuthToken("eyJhbGciOiJIUzI1NiJ9...");
```

### Basic Auth
```java
apiClient.addBasicAuth("username", "password");
```

### Custom Headers
```java
apiClient.addHeader("X-API-Key", "key123");
```

---

## 📊 Validações Disponíveis

```java
// Status code
apiClient.hasStatusCode(200)

// Success (2xx)
apiClient.isSuccessful()

// Response body
apiClient.responseContains("text")
apiClient.getResponseBody()

// Headers
apiClient.getResponseHeader("Content-Type")

// Performance
apiClient.getResponseTime()  // milliseconds
```

---

## 🧪 Testes de Exemplo

Veja `API_REST_AUTOMATION.md` no projeto de testes para:
- UserApiTest (9 testes CRUD)
- ValidationApiTest (8 testes validação + data-driven)

Total: 17 testes API com exemplos práticos

---

## 🔄 Versão

Framework Version: **2.0.0 TRIPLE HYBRID**

Suporta:
- ✅ Mobile (Appium 9.1.0)
- ✅ Web (Selenium 4.20.0)
- ✅ API (REST Assured 5.4.0)

---

## 📚 Documentação

| Arquivo | Localização | Conteúdo |
|---------|-----------|----------|
| **HYBRID_FRAMEWORK.md** | Framework | Framework híbrido (Mobile + Web) |
| **WEB_AUTOMATION.md** | Testes | Automação web |
| **API_REST_AUTOMATION.md** | Testes | **Automação API REST** ← NOVO |

---

**Framework TRIPLE HYBRID: Mobile + Web + API! 🚀**

