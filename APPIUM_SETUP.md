# Guia de Setup - Appium e Android

## Índice
- [Instalar Appium](#instalar-appium)
- [Configurar Android SDK](#configurar-android-sdk)
- [Emulador Android](#emulador-android)
- [Conectar Dispositivo Real](#conectar-dispositivo-real)
- [Troubleshooting](#troubleshooting)

---

## Instalar Appium

### Pré-requisitos
- Node.js 14+ (https://nodejs.org/)
- NPM (incluído com Node.js)

### Instalação (Windows)

```powershell
# 1. Instale Appium globalmente
npm install -g appium

# 2. Instale Appium Doctor para validar setup
npm install -g appium-doctor

# 3. Valide instalação
appium --version
appium-doctor

# 4. Corrija problemas reportados pelo appium-doctor
appium-doctor --android
```

### Instalação (macOS)

```bash
# Usando Homebrew (recomendado)
brew install node
npm install -g appium
npm install -g appium-doctor

# Validar
appium --version
appium-doctor --android
```

### Instalação (Linux)

```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install nodejs npm

npm install -g appium
npm install -g appium-doctor

appium --version
appium-doctor --android
```

---

## Configurar Android SDK

### Windows

#### 1. Instale Android Studio
- Download: https://developer.android.com/studio
- Instale em: `C:\Users\[usuario]\AppData\Local\Android\Sdk`

#### 2. Configure Variáveis de Ambiente

**Painel de Controle → Variáveis de Ambiente:**

```
ANDROID_HOME = C:\Users\[usuario]\AppData\Local\Android\Sdk
ANDROID_SDK_ROOT = %ANDROID_HOME%
JAVA_HOME = C:\Program Files\Java\jdk-17

PATH += %ANDROID_HOME%\platform-tools
PATH += %ANDROID_HOME%\tools
PATH += %ANDROID_HOME%\cmdline-tools\latest\bin
```

#### 3. Inicie Android SDK Manager
```powershell
# Abra Android Studio ou execute via CLI
sdkmanager --list_installed
```

#### 4. Instale componentes necessários
```powershell
# No PowerShell como Admin:
sdkmanager "platform-tools"
sdkmanager "platforms;android-14"
sdkmanager "build-tools;34.0.0"
sdkmanager "system-images;android-14;google_apis;x86_64"
```

### macOS

```bash
# Usando Homebrew
brew install android-sdk
brew install android-platform-tools

# Defina variáveis de ambiente (~/.zshrc ou ~/.bash_profile)
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools

# Instale componentes
sdkmanager "platform-tools"
sdkmanager "platforms;android-14"
sdkmanager "system-images;android-14;google_apis;x86_64"
```

### Linux

```bash
# Ubuntu/Debian
sudo apt-get install android-sdk

# Configure variáveis (~/.bashrc)
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/tools

# Instale componentes
sdkmanager "platform-tools"
sdkmanager "platforms;android-14"
sdkmanager "system-images;android-14;google_apis;x86_64"
```

---

## Emulador Android

### Criar Emulador

```powershell
# Liste AVDs disponíveis
avdmanager list avd

# Crie novo AVD
avdmanager create avd --name "Emulator_Android_14" --package "system-images;android-14;google_apis;x86_64" --device "pixel_4" --force
```

### Iniciar Emulador

```powershell
# Opção 1: Via Android Studio
# Tools → Device Manager → Selecione e clique Play

# Opção 2: Via CLI
emulator -avd Emulator_Android_14

# Opção 3: Com opções extras
emulator -avd Emulator_Android_14 -writable-system -no-boot-anim -no-window
```

### Verificar Emulador Conectado

```powershell
adb devices

# Output esperado:
# List of attached devices
# emulator-5554          device
```

---

## Conectar Dispositivo Real

### Windows

#### 1. Ative Debug USB
- Vá para: **Configurações → Sobre o Telefone**
- Clique 7 vezes em **Número da Compilação**
- Volte para **Configurações → Opções para Desenvolvedores**
- Ative **Depuração USB**

#### 2. Conecte via USB
```powershell
# Conecte smartphone ao PC via USB
# Autorize debug no dispositivo (popup no telefone)

# Verifique conexão
adb devices

# Output esperado:
# List of attached devices
# 192.168.1.100:5555        device
```

### macOS

```bash
# Similar ao Windows
# Ative Developer Mode no iPhone/Android
# Conecte via USB
adb devices
```

### Linux

```bash
# Adiciona permissões
sudo apt-get install android-tools-adb
adb devices

# Se não reconhecer, adicione permissões udev
sudo nano /etc/udev/rules.d/51-android.rules
# Adicione: SUBSYSTEM=="usb", ATTRS{idVendor}=="0e8d", MODE="0666"
sudo service udev restart
```

---

## Iniciar Appium Server

### Via CLI (Recomendado)

```powershell
# Terminal 1: Inicie Appium Server
appium

# Output esperado:
# [Appium] Server is running on 0.0.0.0:4723
```

### Com Parâmetros

```bash
# Especificar porta
appium --port 4723

# Especificar host
appium --address 0.0.0.0

# Com log detalhado
appium --log-level debug

# Combinação
appium --address 0.0.0.0 --port 4723 --log-level info
```

### Via Appium Inspector (GUI)

1. Instale: `npm install -g appium-inspector`
2. Execute: `appium-inspector`
3. Acesse: `http://localhost:4723` ou use a GUI
4. Preencha Desired Capabilities e clique "Start Session"

---

## Verificar Setup

### Test 1: Appium Doctor
```powershell
appium-doctor --android

# Deve retornar "✓" para todos os items:
# ✓ JAVA_HOME set
# ✓ ANDROID_HOME set
# ✓ Android SDK
# ✓ Android Platform Tools
# ✓ Emulator
# ✓ Appium
```

### Test 2: ADB Connection
```powershell
adb devices

# Deve listar:
# emulator-5554          device
# 192.168.x.x:5555       device
```

### Test 3: Appium Server
```powershell
# Terminal 1
appium

# Terminal 2 (enquanto Appium roda)
curl http://127.0.0.1:4723/appium/status

# Retorna JSON status
```

### Test 4: Test Capability
Crie teste simples em `src/test/java/tests/AppiumConnectionTest.java`:

```java
@Test
public void testAppiumConnection() {
    System.out.println("Driver inicializado: " + (driver != null));
    driver.getPageSource();  // Valida conexão
    Assert.assertTrue(true, "Appium conectado com sucesso");
}
```

---

## Atualizar config.properties

```properties
# Após setup completo, configure:

# Para Emulador
android.device.udid=emulator-5554
android.platform.version=14    # Versão do emulador

# Para Dispositivo Real (adb devices)
android.device.udid=192.168.1.100:5555

# App
android.app.path=src/test/resources/apps/app.apk
android.app.package=com.exemplo.app
android.app.activity=.MainActivity

# Appium
appium.server.url=http://127.0.0.1:4723
```

---

## Troubleshooting

### Erro: "ANDROID_HOME not set"
```bash
# Windows
set ANDROID_HOME=C:\Users\[usuario]\AppData\Local\Android\Sdk
echo %ANDROID_HOME%

# macOS/Linux
export ANDROID_HOME=$HOME/Library/Android/sdk
echo $ANDROID_HOME
```

### Erro: "Emulator failed to start"
```bash
# Limpe emulator config
emulator -avd Emulator_Android_14 -wipe-data

# Ou recrie
avdmanager delete avd --name "Emulator_Android_14"
avdmanager create avd --name "Emulator_Android_14" ...
```

### Erro: "Device offline" / "Unauthorized"
```bash
# Reinicie ADB
adb kill-server
adb start-server
adb devices

# Autorize em Developer Options no telefone (popup)
```

### Erro: "Appium port already in use"
```bash
# Identifique processo na porta 4723
netstat -ano | findstr :4723  # Windows
lsof -i :4723                  # macOS/Linux

# Mate processo ou use outra porta
appium --port 4724
```

### Elemento não encontrado / Timeout
1. Verifique XPath com: `adb shell uiautomator dump | grep "elemento"`
2. Use Appium Inspector para inspecionar
3. Aumente timeout em `config.properties`
4. Valide App ou APK em uso

### App não instala
```bash
# Se APK não instala
adb install -r app.apk

# Se persistir, teste manualmente
adb uninstall com.exemplo.app
adb install -r app.apk

# Ou use gradle
./gradlew installDebug
```

---

## Próximas Steps

1. ✅ Instale Appium e Android SDK
2. ✅ Configure variáveis de ambiente
3. ✅ Valide com `appium-doctor`
4. ✅ Crie/configure emulador ou conecte device
5. ✅ Teste conexão: `adb devices`
6. ✅ Inicie Appium Server: `appium`
7. ✅ Configure `config.properties`
8. ✅ Execute teste: `mvn test`

---

## Recursos Úteis

- [Appium Android Docs](https://appium.io/docs/en/latest/guides/environments/android/)
- [Android SDK Setup](https://developer.android.com/studio/install)
- [ADB Commands](https://developer.android.com/studio/command-line/adb)
- [UI Automator Viewer](https://developer.android.com/studio/profile/ui-automator)

---

**Última atualização**: Maio 2026
**Versão Android Recomendada**: 14 (API 34)
**Java Version**: 17

