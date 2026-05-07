# Guia de Desenvolvimento - Mobile Automation Framework

## Sumário
- [Setup do Desenvolvedor](#setup-do-desenvolvedor)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Como Adicionar Novos Testes](#como-adicionar-novos-testes)
- [Padrões de Código](#padrões-de-código)
- [Debugging](#debugging)
- [Contribuindo](#contribuindo)

---

## Setup do Desenvolvedor

### Requisitos
- Java 17+
- Maven 3.8+
- IDE (IntelliJ IDEA, VSCode, etc.)
- Git

### Instalação Local

```bash
# 1. Clone o projeto
git clone https://github.com/seu-usuario/mobile-automation-framework-java.git
cd mobile-automation-framework-java

# 2. Instale dependências
mvn clean install

# 3. Configure config.properties
# Edite: src/test/resources/config.properties

# 4. Inicie Appium Server (em outro terminal)
appium

# 5. Execute os testes
mvn test
```

---

## Estrutura de Pastas

```
src/test/java/
├── base/                    # Classes base reutilizáveis
│   ├── BaseTest.java       # Classe base para testes (setup/teardown)
│   └── BasePage.java       # Classe base para Page Objects
├── drivers/                # Gerenciamento de drivers
│   └── DriverFactory.java  # Factory para criar drivers Appium
├── pages/                  # Page Objects (uma por tela da app)
│   ├── LoginPage.java
│   ├── HomePage.java
│   └── SettingsPage.java
├── tests/                  # Testes automatizados
│   ├── LoginTest.java
│   ├── HomeTest.java
│   └── DataDrivenLoginTest.java
└── utils/                  # Utilitários e helpers
    ├── ConfigReader.java
    ├── LoggerUtil.java
    ├── WaitUtils.java
    ├── ScreenshotUtil.java
    └── AssertionUtil.java
```

---

## Como Adicionar Novos Testes

### 1. Criar uma nova Page Object

Crie um arquivo em `src/test/java/pages/` para cada tela/página:

```java
package com.mobile.automation.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import com.mobile.automation.base.BasePage;
import com.mobile.automation.utils.LoggerUtil;
import com.mobile.automation.utils.WaitUtils;

public class NewPage extends BasePage {
    
    // Localizadores
    private final By elementName = By.xpath("//xpath[@attribute='value']");
    
    public NewPage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils);
    }
    
    public void performAction() {
        LoggerUtil.info("Realizando ação");
        click(elementName);
    }
    
    public boolean isPageDisplayed() {
        return isElementDisplayed(elementName);
    }
}
```

### 2. Criar um novo Teste

Crie um arquivo em `src/test/java/tests/`:

```java
package com.mobile.automation.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import com.mobile.automation.base.BaseTest;
import com.mobile.automation.pages.NewPage;
import com.mobile.automation.utils.LoggerUtil;

@Epic("Feature Name")
@Feature("Sub Feature")
public class NewTest extends BaseTest {
    
    private NewPage newPage;
    
    @Override
    public void setUp() {
        super.setUp();
        newPage = new NewPage(driver, waitUtils);
    }
    
    @Test(description = "Test description")
    public void testSomething() {
        LoggerUtil.info("Iniciando teste");
        
        // Arrange
        Assert.assertTrue(newPage.isPageDisplayed());
        
        // Act
        newPage.performAction();
        
        // Assert
        Assert.assertTrue(true);
    }
}
```

---

## Padrões de Código

### BaseTest - Estrutura Base para Testes

Todos os testes devem herdar de `BaseTest`:

```java
public class MyTest extends BaseTest {
    // driver e waitUtils já estão inicializados
    // métodos BeforeMethod e AfterMethod executam automaticamente
}
```

### BasePage - Estrutura Base para Page Objects

Todos os Page Objects devem herdar de `BasePage`:

```java
public class MyPage extends BasePage {
    public MyPage(AppiumDriver driver, WaitUtils waitUtils) {
        super(driver, waitUtils); // Obrigatório
    }
    
    // Use os métodos herdados:
    // - click(By)
    // - sendKeys(By, String)
    // - getText(By)
    // - isElementDisplayed(By)
    // - waitForElement(By)
}
```

### Naming Conventions

| Elemento | Convenção | Exemplo |
|----------|-----------|---------|
| Classe de Teste | `*Test` | `LoginTest.java` |
| Método de Teste | `test*` | `testLoginSuccess()` |
| Page Object | `*Page` | `LoginPage.java` |
| Localizador | `camelCase` | `loginButton` |
| Variável privada | `camelCase` | `implicitWait` |

### Localizadores (By)

Prefira XPath com IDs quando possível:

```java
// ✅ Bom
private final By loginButton = By.xpath("//button[@resource-id='com.app:id/login_btn']");
private final By emailField = By.id("email_input");

// ❌ Evitar
private final By veryLongXpath = By.xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout[0]/...");
```

### Logging

Sempre use `LoggerUtil` para logging:

```java
LoggerUtil.info("Iniciando teste");
LoggerUtil.debug("Valor debug: " + valor);
LoggerUtil.passStep("Ação realizada com sucesso");
LoggerUtil.failStep("Ação falhou");
LoggerUtil.error("Erro encontrado");
LoggerUtil.testInfo("testName", "Mensagem");
```

---

## Debugging

### 1. Android Studio / Android Inspector

Inspecione elementos em tempo real:

```bash
# Terminal 1: Inicie Appium
appium

# Terminal 2: Abra inspetor Appium
npx appium-inspector

# Conecte em http://127.0.0.1:4723
```

### 2. Capturar Screenshots em Testes

```java
public void testWithScreenshot() {
    LoggerUtil.info("Teste com screenshot");
    
    // Seu teste aqui
    
    // Capture screenshot
    String screenshotPath = ScreenshotUtil.takeScreenshot(driver, "test-name");
    LoggerUtil.info("Screenshot: " + screenshotPath);
}
```

### 3. Aumentar Verbosidade de Logs

Edite `src/test/resources/log4j2.xml`:

```xml
<Logger name="com.mobile.automation" level="debug" additivity="false">
    <AppenderRef ref="ConsoleAppender"/>
    <AppenderRef ref="RollingFileAppender"/>
</Logger>
```

### 4. Executar Teste Específico

```bash
# Um teste
mvn test -Dtest=LoginTest#testLoginSuccess

# Uma classe
mvn test -Dtest=LoginTest

# Com padrão
mvn test -Dtest=*LoginTest
```

---

## Waits - Melhorias e Cuidados

### ✅ Use Waits Explícitos

```java
// Correto
WebElement element = waitUtils.waitForElementToBeClickable(locator);
element.click();

// Também correto
click(locator); // BasePage já trata os waits
```

### ❌ Evite Thread.sleep()

```java
// Ruim
Thread.sleep(5000);

// Melhor
waitUtils.waitForElementToBeVisible(locator);

// Aceitável (apenas em casos extremos)
WaitUtils.waitSeconds(2);
```

---

## Configurações

### config.properties - Principais Chaves

```properties
# Ambiente
environment=local|dev|staging|prod
platform=Android|iOS

# Android
android.app.path=/caminho/app.apk
android.device.udid=emulator-5554
android.platform.version=14
android.app.package=com.example.app
android.app.activity=.MainActivity

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

---

## Relatórios

### Allure Reports

```bash
# Executar testes com Allure
mvn clean test

# Gerar relatório
mvn allure:report

# Visualizar em navegador
mvn allure:serve
```

O relatório inclui:
- Resumo de execução
- Taxa de sucesso/falha
- Timeline de execução
- Logs detalhados
- Screenshots

---

## Contribuindo

### Workflow de Contribuição

1. **Create Feature Branch**
```bash
git checkout -b feature/minha-feature
```

2. **Desenvolva e Teste**
```bash
mvn clean test
```

3. **Commit com Mensagens Claras**
```bash
git commit -m "Add: Novo teste para Login"
```

4. **Push e Crie PR**
```bash
git push origin feature/minha-feature
```

### Checklist para PRs

- [ ] Código segue padrões do projeto
- [ ] Testes executam com sucesso (`mvn test`)
- [ ] Sem warnings do compiler
- [ ] Logs apropriados adicionados
- [ ] Documentação atualizada (README se necessário)
- [ ] Sem código comentado ou debug

### Versionamento

Projeto usa [Semantic Versioning](https://semver.org/):
- `MAJOR` - Breaking changes
- `MINOR` - New features (backward compatible)
- `PATCH` - Bug fixes

---

## FAQ

**P: Como adicionar nova dependência Maven?**
R: Edite `pom.xml` e adicione no `<dependencies>`, depois execute `mvn clean install`.

**P: Como executar testes em paralelo?**
R: Edite `testng.xml`: `<suite parallel="methods" thread-count="4">`

**P: Como mudar nível de log?**
R: Edite `log4j2.xml` ou `ConfigReader.java`

**P: Como integrar com CI/CD?**
R: Veja `.github/workflows/tests.yml` para exemplo GitHub Actions

---

## Recursos Úteis

- [Appium Documentation](https://appium.io/docs/en/latest/)
- [Selenium WebDriver](https://www.selenium.dev/documentation/)
- [TestNG](https://testng.org/doc/)
- [Allure Reports](https://docs.qameta.io/allure/)
- [Log4j2](https://logging.apache.org/log4j/2.x/)

---

**Última atualização**: Maio 2026
**Versão do Framework**: 1.0.0

