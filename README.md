# Employee Self-Service Mobile Application

## 📱 Project Overview

A mobile Employee Self-Service application designed to provide employees with secure and convenient access to their employment-related information through a simple and user-friendly interface.

The application integrates with REST APIs to retrieve employee-specific information and provides centralized access to commonly required employee services.

## ✨ Features

- 👤 **Employee Profile** — View personal and employment-related information.
- 💰 **Salary Information** — Access salary details based on the selected financial year.
- 🧾 **Income Tax** — View employee-specific income tax information and annual tax records.
- 🏦 **Provident Fund** — Access provident fund information through the application.
- 💳 **PF Loan** — View provident fund loan-related information.
- 🗓️ **Leave History** — View previously recorded employee leave information.
- 📄 **Employee Documents** — Access available employment-related documents.
- 🔌 **REST API Integration** — Retrieve employee information dynamically from backend services.
- 🔐 **Employee-Specific Data Access** — Display information associated with the authenticated/selected employee.
- 📊 **Dynamic Data Display** — Present API data in organized and easy-to-read mobile screens.
- 📱 **User-Friendly Interface** — Clean Android interface designed for convenient employee access.
- ⚡ **API-Based Information Retrieval** — Fetch updated information instead of relying on static data.
- 🔄 **Financial-Year Based Records** — Retrieve salary and tax information according to the relevant financial year.

## 🛠️ Technologies

- **Kotlin** — Android application development
- **Android** — Mobile application platform
- **Jetpack Compose** — Modern declarative UI development
- **Android Studio** — Android development environment
- **REST APIs** — Backend communication
- **JSON** — API request/response data format
- **Gradle** — Project build and dependency management

## 📂 Project Modules

The application consists of the following major modules:

### 👤 Employee Profile
Provides access to employee-specific profile and employment information.

### 💰 Salary Information
Allows employees to retrieve and view salary information according to the selected financial year.

### 🧾 Income Tax
Provides access to employee-specific income tax information and financial-year based tax records.

### 🏦 Provident Fund
Displays provident fund-related information associated with the employee.

### 💳 PF Loan
Provides information related to provident fund loans.

### 🗓️ Leave History
Allows employees to view their available leave history and related records.

### 📄 Employee Documents
Provides centralized access to available employee-related documents.

## 🔌 API Integration

The application communicates with backend services through REST APIs.

### Employee API

Used to retrieve available employee identifiers and employee-related information.

### Salary API

Used to retrieve salary information based on:

- Employee ID
- Financial Year

### Income Tax API

Used to retrieve income tax information based on:

- Employee ID
- Financial Year

API responses are received in **JSON format**, processed by the application, and displayed through the Jetpack Compose user interface.

## 📸 Screenshots

### Login
![Login](screenshots/login.png)

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Salary
![Salary](screenshots/salary.png)

### Income Tax
![Income Tax](screenshots/income-tax.png)

### Profile
![Profile](screenshots/profile.png)

### Splash Screen
![Splash](screenshots/splash.png)

## 📸 App Screenshots

<p align="center">
  <img src="screenshots/splash.png" width="200">
  <img src="screenshots/login.png" width="200">
  <img src="screenshots/dashboard.png" width="200">
</p>

<p align="center">
  <img src="screenshots/salary.png" width="200">
  <img src="screenshots/income-tax.png" width="200">
  <img src="screenshots/profile.png" width="200">
</p>

## 🚀 Installation

### Prerequisites

- Android Studio
- Android SDK
- JDK
- Android device or emulator
- Required backend/API access

### Setup

Clone the repository:

```bash
git clone https://github.com/Ayesha-shahzadi15/employeeservice.git
