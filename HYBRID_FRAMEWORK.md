# 🌐 Framework Híbrido: Mobile + Web

## ✨ O que é Novo

O **mobile-automation-framework** agora é **HÍBRIDO**, suportando testes tanto MOBILE (Appium) quanto WEB (Selenium WebDriver).

---

## 📦 Novas Classes Framework

### 1. WebDriverFactory.java
**Localização:** `src/main/java/com/mobile/automation/drivers/WebDriverFactory.java`

**Responsabilidade:** Gerenciar criação e configuração de drivers web (Chrome, Firefox, Edge, Safari)

**Métodos principais:**
- `createWebDriver()` - Cria driver baseado no browser configurado
- `createChromeDriver()` - Chrome com WebDriverManager
- `createFirefoxDriver()` - Firefox
- `createEdgeDriver()` - Edge
- `createSafariDriver()` - Safari
- `quitWebDriver(WebDriver)` - Fecha driver
- `maximizeWindow(WebDriver)` - Maximiza janela

### 2. BaseTest Expandido
**Localização:** `src/main/java/com/mobile/automation/base/BaseTest.java`

**Mudanças:**
- Suporte a `AppiumDriver` (mobile) E `WebDriver` (web)
- Método `setUp()` detecta `test.type` e inicia driver apropriado
- `setUpMobile()` - Inicializa Appium
- `setUpWeb()` - Inicializa Selenium
- Métodos helpers: `isMobileTest()`, `isWebTest()`

### 3. TestContext Expandido
**Localização:** `src/main/java/com/mobile/automation/integration/TestContext.java`

**Mudanças:**
- Armazena tanto `AppiumDriver` quanto `WebDriver`
- `setTestType(String)` - Define tipo de teste
- `isMobileTest()` / `isWebTest()` - Verificar tipo
- `cleanUp()` - Fecha ambos os drivers

### 4. ConfigReader Expandido
**Localização:** `src/main/java/com/mobile/automation/utils/ConfigReader.java`

**Novos métodos:**
- `getBrowser()` - Obtém browser configurado
- `getWebBaseUrl()` - URL base da aplicação web
- `isHeadlessMode()` - Modo headless
- `isIncognitoMode()` - Modo incógnito/privado
- `getPageLoadTimeout()` - Timeout de carregamento
- `getMaxRetry()` - Máximo de retries

---

## 🔧 Dependências Adicionadas

```xml
<!-- Já incluído em pom.xml -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.20.0</version>
</dependency>

<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.8.0</version>
</dependency>
```

---

## ⚙️ Configuração

### config.properties

```properties
# Tipo de teste
test.type=mobile    # ou 'web'

# Web (quando test.type=web)
browser=chrome      # chrome, firefox, edge, safari
web.base.url=http://localhost:3000
headless.mode=false
incognito.mode=false
page.load.timeout=30

# Mobile (quando test.type=mobile)
platform=android
appium.server.url=http://localhost:4723
```

---

## 📝 Como Usar como Framework

### Em seu projeto de testes:

**pom.xml:**
```xml
<dependency>
    <groupId>com.mobile.automation</groupId>
    <artifactId>mobile-automation-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Para testes WEB:**
```java
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.drivers.WebDriverFactory;
import com.mobile.test.pages.web.LoginPageWeb;

public class MyWebTest extends BaseTest {
    @Test
    public void testLogin() {
        LoginPageWeb loginPage = new LoginPageWeb(webDriver);
        loginPage.enterEmail("user@example.com")
                 .enterPassword("password")
                 .clickLogin();
    }
}
```

**Para testes MOBILE:**
```java
import com.mobile.automation.base.BaseTest;
import com.mobile.test.pages.LoginPage;

public class MyMobileTest extends BaseTest {
    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("user@example.com")
                 .enterPassword("password")
                 .clickLogin();
    }
}
```

---

## 🎯 Fluxo de Execução

### Para Testes WEB:

```
BaseTest.setUp()
    ↓
Detecta: test.type=web
    ↓
setUpWeb()
    ↓
WebDriverFactory.createWebDriver()
    ↓
BrowserOptions (headless, incognito, etc)
    ↓
webDriver.navigate() → web.base.url
    ↓
Teste Executado
    ↓
BaseTest.tearDown()
    ↓
WebDriverFactory.quitWebDriver()
```

### Para Testes MOBILE:

```
BaseTest.setUp()
    ↓
Detecta: test.type=mobile
    ↓
setUpMobile()
    ↓
DriverFactory.createDriver()
    ↓
AppiumDriver (Android/iOS)
    ↓
Teste Executado
    ↓
BaseTest.tearDown()
    ↓
driver.quit()
```

---

## 🚀 Versão Maven

Atualizar versão do framework:

```xml
<!-- Em seu pom.xml -->
<dependency>
    <groupId>com.mobile.automation</groupId>
    <artifactId>mobile-automation-framework</artifactId>
    <version>2.0.0</version>  <!-- Híbrido -->
</dependency>
```

---

## 📊 Classes Disponíveis

| Classe | Localização | Função |
|--------|-----------|---------|
| **WebDriverFactory** | `drivers/` | ✅ NOVO - Factory para drivers web |
| **BaseTest** | `base/` | ✅ EXPANDIDO - Suporta mobile + web |
| **TestContext** | `integration/` | ✅ EXPANDIDO - Contexto híbrido |
| **ConfigReader** | `utils/` | ✅ EXPANDIDO - Lê configs web |
| **BasePage** | `base/` | ✅ Funciona para ambos |
| **DriverFactory** | `drivers/` | ✅ Appium (mobile) |

---

## 🔍 Detectar Tipo de Teste

```java
if (testContext.isMobileTest()) {
    // Usar mobile driver
    AppiumDriver mobileDriver = testContext.getDriver();
}

if (testContext.isWebTest()) {
    // Usar web driver
    WebDriver webDriver = testContext.getWebDriver();
}
```

---

## 📚 Documentação Adicional

Veja `WEB_AUTOMATION.md` no projeto de testes para:
- Page Objects Web de exemplo
- Test Classes Web de exemplo
- Configuração detalhada
- Troubleshooting

---

## ✅ Recursos Suportados

| Recurso | Mobile | Web |
|---------|--------|-----|
| **BaseTest** | ✅ | ✅ |
| **Listeners** | ✅ | ✅ |
| **Relatórios Allure** | ✅ | ✅ |
| **JIRA Integration** | ✅ | ✅ |
| **Data-Driven** | ✅ | ✅ |
| **Screenshots** | ✅ | ✅ |
| **Logging** | ✅ | ✅ |
| **Retry** | ✅ | ✅ |

---

**Framework v2.0.0 - HÍBRIDO (Mobile + Web) 🚀**

