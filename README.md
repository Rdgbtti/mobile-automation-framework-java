# 🚀 Mobile Automation Framework - Java

Um framework **Triple Hybrid** robusto e modular para automação de testes em aplicações **Mobile** (Android/iOS com Appium), **Web** (Chrome/Firefox com Selenium) e **APIs REST** usando TestNG, com integração JIRA, listeners automáticos e relatórios Allure.

**Desenvolvido por:** [Rdgbtti](https://github.com/Rdgbtti/Rdgbtti)

## 📋 Índice

- [Características](#características)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Uso](#uso)
- [Exemplos de Uso](#exemplos-de-uso)
- [Padrões e Melhores Práticas](#padrões-e-melhores-práticas)
- [Relatórios](#relatórios)
- [CI/CD](#cicd)
- [Troubleshooting](#troubleshooting)
- [Contribuindo](#contribuindo)
- [Suporte](#suporte)

## ✨ Características

- ✅ **Automação Mobile**: Android e iOS via Appium (Appium 9.1.0)
- ✅ **Automação Web**: Chrome, Firefox, Edge, Safari via Selenium (4.20.0)
- ✅ **REST API**: Testes de API com REST Assured (5.4.0)
- ✅ **Page Object Model**: Organização modular e moderno
- ✅ **TestNG**: Framework poderoso com @DataProvider (data-driven)
- ✅ **Logging**: Log4j2 com níveis configuráveis
- ✅ **Relatórios**: Allure Reports com screenshots automáticos
- ✅ **JIRA Integration**: Link testes com issues JIRA
- ✅ **Listeners**: Retry automático, captura de falhas/sucesso/skipped
- ✅ **Java 17**: Features modernas da linguagem
- ✅ **Maven**: Dependências atualizadas e gerenciadas

## 📦 Requisitos

- **Java 17** ou superior
- **Maven 3.8** ou superior
- **Node.js** (para Appium)
- **Appium Server** (2.x ou superior)
- **Android SDK** (para testes mobile Android)
- **Xcode** (para testes iOS em macOS)
- **Navegadores**: Chrome, Firefox, Edge ou Safari (para testes web)

---

## 🏗️ Arquitetura Triple Hybrid

Este é um framework **hybrid** que suporta 3 tipos de automação:

```
┌─────────────────────────────────────────────────────────┐
│         MOBILE AUTOMATION FRAMEWORK v3.0.0              │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  📱 MOBILE (Appium)    🌐 WEB (Selenium)    🔌 API (REST)  │
│  ├─ Android            ├─ Chrome             ├─ GET      │
│  ├─ iOS                ├─ Firefox            ├─ POST     │
│  └─ Emuladores         ├─ Safari             ├─ PUT      │
│                        └─ Edge               ├─ PATCH    │
│                                              └─ DELETE   │
│                                                          │
│  Todos com: TestNG • Allure • JIRA • Listeners         │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Detecta automaticamente o tipo de teste em `config.properties`:

```properties
# Mobile
test.type=mobile
platform=android

# Web
test.type=web
browser=chrome

# API
test.type=api
api.base.url=http://localhost:8080
```

---

### 🔧 Instalar Appium

```bash
npm install -g appium
npm install -g appium-doctor

# Verificar instalação
appium --version
appium-doctor
```

## 🚀 Instalação

### 1️⃣ Clone o projeto Framework

```bash
git clone https://github.com/Rdgbtti/Rdgbtti.git
cd mobile-automation-framework-java
```

### 2️⃣ Instale as dependências

```bash
mvn clean install
```

Isto vai baixar:
- ✅ Appium 9.1.0 (Mobile)
- ✅ Selenium 4.20.0 (Web)
- ✅ REST Assured 5.4.0 (API)
- ✅ TestNG 7.9.0 (Testes)
- ✅ Allure 2.23.1 (Relatórios)
- ✅ JIRA Client 8.26.0 (Integração)

### 3️⃣ Verifique a instalação dos pré-requisitos

```bash
java -version          # Java 17+
mvn -v                 # Maven 3.8+
appium --version       # Appium 2.x+
node --version         # Node.js
appium-doctor          # Diagnóstico Appium
```

## 📁 Estrutura do Projeto

```
mobile-automation-framework-java/
│
├── src/
│   ├── main/
│   │   └── java/                      # Código do framework
│   │
│   └── test/
│       ├── java/
│       │   ├── base/                  # Classes base
│       │   │   ├── BaseTest.java     # Base para todos os testes
│       │   │   └── BasePage.java     # Base para Page Objects
│       │   │
│       │   ├── drivers/               # Gerenciamento de drivers
│       │   │   └── DriverFactory.java # Factory para criar drivers
│       │   │
│       │   ├── pages/                 # Page Object Model
│       │   │   ├── LoginPage.java
│       │   │   └── HomePage.java
│       │   │
│       │   ├── tests/                 # Classes de teste
│       │   │   ├── LoginTest.java
│       │   │   └── HomeTest.java
│       │   │
│       │   └── utils/                 # Utilitários
│       │       ├── ConfigReader.java
│       │       ├── LoggerUtil.java
│       │       └── WaitUtils.java
│       │
│       └── resources/
│           ├── config.properties      # Configurações
│           ├── log4j2.xml            # Configuração de logs
│           └── testng.xml            # Configuração TestNG
│
├── .github/
│   └── workflows/
│       └── tests.yml                  # GitHub Actions workflow
│
├── pom.xml                            # Dependências Maven
├── README.md                          # Este arquivo
├── .gitignore                         # Git ignore rules
```

## ⚙️ Configuração

### 1. Configurar config.properties

O arquivo `src/test/resources/config.properties` contém todas as configurações:

```properties
# Android
android.app.path=src/test/resources/apps/app.apk
android.device.udid=emulator-5554
android.platform.version=14
android.app.package=com.example.app
android.app.activity=.MainActivity
android.auto.grant.permissions=true

# iOS
ios.device.name=iPhone Simulator
ios.platform.version=17.0
ios.bundle.id=com.example.app

# Appium
appium.server.url=http://127.0.0.1:4723

# Timeouts (segundos)
implicit.wait=10
explicit.wait=15
page.load.timeout=30
```

### 2. Iniciar Appium Server

Você pode iniciar via CLI ou usar a integração do framework:

```bash
# Via CLI
appium

# Ou deixar que o framework inicie automaticamente (configurar em DriverFactory.java)
```

## 💡 Uso

### Executar testes

```bash
# Todos os testes (detecta tipo automaticamente)
mvn clean test

# Apenas testes móbile
mvn clean test -Dtest=*Test -Dtest.type=mobile

# Apenas testes web
mvn clean test -Dtest=*Test -Dtest.type=web

# Apenas testes API
mvn clean test -Dtest=*Test -Dtest.type=api

# Teste específico
mvn clean test -Dtest=LoginTest

# Gerar relatório Allure
mvn allure:serve
```

---

## 📖 Exemplos de Uso

### 📱 Teste Mobile (Appium)

```java
@Test
public void testMobileLogin() {
    LoginPage loginPage = new LoginPage(driver, waitUtils);
    
    loginPage.enterEmail("usuario@example.com");
    loginPage.enterPassword("senha123");
    loginPage.clickLogin();
    
    Assert.assertTrue(loginPage.isHomeScreenDisplayed());
}
```

### 🌐 Teste Web (Selenium)

```java
@Test
public void testWebLogin() {
    driver.get("https://app.example.com");
    LoginPage loginPage = new LoginPage(driver, waitUtils);
    
    loginPage.enterEmail("usuario@example.com");
    loginPage.enterPassword("senha123");
    loginPage.clickLogin();
    
    Assert.assertTrue(loginPage.isHomePageDisplayed());
}
```

### 🔌 Teste API (REST Assured)

```java
@Test
public void testApiCreateUser() {
    RestApiClient apiClient = new RestApiClient(api.baseUrl);
    
    JsonObject payload = new JsonObject();
    payload.addProperty("name", "João");
    payload.addProperty("email", "joao@example.com");
    
    Response response = apiClient.post("/users", payload.toString());
    
    Assert.assertTrue(apiClient.isSuccessful());
    Assert.assertTrue(apiClient.responseContains("id"));
}
```

---

## 🎯 Listeners TestNG e JIRA Integration

### Listeners Automáticos

O framework captura automaticamente:
- ✅ **Sucessos**: Registra testes que passaram
- ✅ **Falhas**: Captura screenshots e erros
- ✅ **Skipped**: Identifica testes pulados
- ✅ **Retry**: Retenta testes falhados automaticamente

```java
// Disponível automaticamente, sem config extra!
@Test
public void testLogin() {
    // Se falhar:
    // 1. Screenshot é capturado
    // 2. Erro é registrado em log
    // 3. Teste é retentado 1x
    // 4. Resultado é enviado para JIRA
}
```

### Integração JIRA

Link seus testes com issues do JIRA:

```java
@Test
@JiraIssue("PROJ-123")  // Link com issue JIRA
public void testLoginFlow() {
    // O resultado deste teste será enviado para PROJ-123
}
```

---

## 📚 Padrões e Melhores Práticas

### Page Object Model

Cada página/tela da aplicação deve ter uma classe correspondente:

```java
public class LoginPage extends BasePage {
    // Localizadores
    private final By emailField = By.xpath("...");
    private final By passwordField = By.xpath("...");
    
    // Métodos de ação
    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }
    
    // Métodos de verificação
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(emailField);
    }
}
```

### Waits Explícitos

Sempre use `WaitUtils` em vez de esperas implícitas:

```java
// ✅ Correto
WebElement element = waitUtils.waitForElementToBeClickable(locator);

// ❌ Evitar
Thread.sleep(5000);
```

### Logging

Use `LoggerUtil` para logging centralizado:

```java
LoggerUtil.info("Iniciando teste");
LoggerUtil.passStep("Ação realizada com sucesso");
LoggerUtil.failStep("Ação falhou");
LoggerUtil.error("Erro encontrado");
```

## 📊 Relatórios

### Allure Reports

O framework está configurado para gerar Allure Reports automaticamente.

```bash
# Gerar relatório
mvn allure:report

# Visualizar relatório
mvn allure:serve
```

O relatório será aberto no navegador com:
- Resumo dos testes
- Taxa de sucesso/falha
- Histórico de execuções
- Logs detalhados
- Screenshots (se capturados)

## 🔄 CI/CD

### GitHub Actions

O arquivo `.github/workflows/tests.yml` executa os testes automaticamente em cada push:

```yaml
name: Mobile Automation Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    strategy:
      matrix:
        java-version: [17]
    
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: ${{ matrix.java-version }}
      - run: mvn clean test
```

## 📝 Exemplo de Teste Completo

```java
@Epic("Autenticação")
@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver, waitUtils);
    }

    @Test
    @Story("Login com sucesso")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver, waitUtils);
        
        loginPage.login("user@example.com", "password123");
        
        Assert.assertTrue(loginPage.isHomePageDisplayed());
    }
}
```

## 🐛 Troubleshooting

### Appium não inicia
```bash
appium-doctor
# Verifique e corrija os problemas relatados
```

### Timeout em elementos
- Aumente `explicit.wait` em config.properties
- Verifique se o localizador está correto
- Use `Developer Tools` do Android/iOS para inspecionar elementos

### Driver não inicializa
- Verifique se Appium Server está rodando
- Confirme `appium.server.url` em config.properties
- Valide `desired capabilities` em DriverFactory.java

## 🤝 Contribuindo

1. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
2. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
3. Push para a branch (`git push origin feature/AmazingFeature`)
4. Abra um Pull Request

## 📄 Licença

Distribuído sob a licença MIT. Veja LICENSE para mais informações.

## 👥 Autor

- **Rdgbtti** - [GitHub](https://github.com/Rdgbtti/Rdgbtti)

## 📞 Suporte e Contato

Para dúvidas, sugestões ou issues:
- 🐙 **GitHub**: [Rdgbtti](https://github.com/Rdgbtti/Rdgbtti)

## 📄 Licença

Distribuído sob a licença MIT. Veja LICENSE para mais informações.

---

**Última atualização**: Maio 2026  
**Versão**: 3.0.0 - Triple Hybrid (Mobile + Web + API)

