# 📱 Android Automation Framework (Appium + Java + TestNG)

## 1. Introduction

This project is an **Android mobile automation testing framework** built with **Appium 2**, **Java**, and **TestNG**, following **Page Object Model (POM)** and **Page Generator** design patterns.


---

## 2. Technology Stack

| Component          | Recommended Version       |
| ------------------ |---------------------------|
| Java               | 21                        |
| NodeJS             | 24.x (LTS)                |
| Appium             | 3.0.0+                    |
| Appium Java Client | 9.x                       |
| Selenium           | 4.x                       |
| TestNG             | 7.x                       |
| Android Emulator   | Android 12–13 (API 31–33) |

---

## 3. Environment Setup

### 3.1 Java

```bash
java -version
```

### 3.2 NodeJS & Appium

```bash
node -v
npm -v

npm install -g appium@3.1.2
appium -v
```

Install Android driver:

```bash
appium driver install uiautomator2
```

---

### 3.3 Android SDK & Emulator

* Install **Android Studio**
* Create an emulator:

    * Android 12 (API 31) or Android 13 (API 33)

Verify device:

```bash
adb devices
```

Expected output:

```
emulator-5554    device
```

---

## 4. Project Structure

```
src
 ├── main
 │   └── java
 │       ├── core
 │       │   ├── BaseTest.java
 │       │   └── DriverFactory.java
 │       ├── commons
 │       │   └── BasePage.java
 │       ├── pageObjects
 │       │   ├── HomePage.java
 │       │   └── PageGenerator.java
 │       └── pageUIs
 │           └── HomePageUI.java
 └── test
     └── java
         └── com.orangehrm
             └── TC_01.java
```

---

## 5. Driver Configuration

### 5.1 DriverFactory

```java
public class DriverFactory {
    public static AndroidDriver initDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
            .setDeviceName("emulator-5554")
            .setAutomationName("UiAutomator2")
            .setAppPackage("io.appium.android.apis")
            .setAppActivity(".ApiDemos")
            .setNoReset(true);

        return new AndroidDriver(
            new URL("http://127.0.0.1:4723/"), options);
    }
}
```
---