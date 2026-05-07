# Mobile Automation Framework - Java

Framework robusto e modular para automação de testes em aplicações móveis (Android e iOS) usando **Appium**, **Selenium** e **TestNG**.

## 📋 Índice

- [Características](#características)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Uso](#uso)
- [Padrões e Melhores Práticas](#padrões-e-melhores-práticas)
- [Relatórios](#relatórios)
- [CI/CD](#cicd)

## ✨ Características

- ✅ **Automação Mobile**: Suporte para Android e iOS via Appium
- ✅ **Page Object Model**: Organização modular e reutilizável
- ✅ **TestNG**: Framework de testes poderoso com data-driven capabilities
- ✅ **Logging**: Log4j2 com níveis configuráveis
- ✅ **Relatórios**: Allure Reports para visualização de resultados
- ✅ **CI/CD**: GitHub Actions workflow pré-configurado
- ✅ **Java 17**: Utilizando features modernas do Java
- ✅ **Maven**: Gerenciamento de dependências e builds

## 📦 Requisitos

- **Java 17** ou superior
- **Maven 3.8** ou superior
- **Android SDK** (para testes Android)
- **Xcode** (para testes iOS)
- **Appium Server** (2.x ou superior)
- **Node.js** (para executar Appium)

### Instalar Appium

```bash
npm install -g appium
npm install -g appium-doctor

# Verificar instalação
appium --version
appium-doctor
```

## 🚀 Instalação

### 1. Clone o projeto

```bash
git clone https://github.com/seu-usuario/mobile-automation-framework-java.git
cd mobile-automation-framework-java
```

### 2. Instale as dependências

```bash
mvn clean install
```

### 3. Configure o arquivo `config.properties`

Edite `src/test/resources/config.properties` com seus dados:

```properties
# Ambiente
environment=local
platform=Android

# Configurações Android
android.app.path=/caminho/para/app.apk
android.device.udid=emulator-5554
android.platform.version=14
android.app.package=com.example.app
android.app.activity=.MainActivity

# Servidor Appium
appium.server.url=http://127.0.0.1:4723

# Timeouts
implicit.wait=10
explicit.wait=15
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

### 1. Criar um novo Page Object

```java
public class HomePage extends BasePage {
    private final By homeButton = By.xpath("//button[@text='Home']");
    
    public HomePage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils);
    }
    
    public void clickHomeButton() {
        click(homeButton);
    }
}
```

### 2. Criar um novo Teste

```java
@Test
public void testHomeNavigation() {
    HomePage homePage = new HomePage(driver, waitUtils);
    homePage.clickHomeButton();
    Assert.assertTrue(homePage.isHomePageDisplayed());
}
```

### 3. Executar testes

```bash
# Todos os testes
mvn clean test

# Teste específico
mvn clean test -Dtest=LoginTest

# Com padrão específico
mvn clean test -Dtest=*Test

# Gerar Allure Report
mvn clean test
mvn allure:report
```

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

## 👥 Autores

- **Seu Nome** - *Trabalho Inicial*

## 📞 Suporte

Para mais informações ou suporte:
- 📧 Email: seu.email@example.com
- 🐙 GitHub: https://github.com/seu-usuario
- 📱 LinkedIn: https://linkedin.com/in/seu-perfil

---

**Última atualização**: Maio 2026

