# SaidounBelabbas
Structured Documents Processing Engine (Java / XML)
# 🧩 Structured Data Processing & Validation Engine (Java / XML)

A Java-based engine designed to process, transform, and normalize structured documents from multiple formats into standardized XML outputs.

This project was developed as part of an academic module at USTHB and demonstrates strong capabilities in data parsing, transformation, and validation.

---

## 🚀 Features

- XML parsing and transformation using DOM APIs  
- Processing of heterogeneous data sources (XML, TXT, HTML, FXML)  
- Recursive directory traversal and automated file handling  
- Generation of structured XML outputs (custom schemas, TEI-like format)  
- Data extraction from HTML and JavaFX (FXML) files  
- Handling multilingual structured data (Arabic / French)  

---

## 🧠 Use Cases

- Structured data transformation  
- Data validation pipelines  
- XML normalization  
- Backend data processing simulation  
- Foundations for API payload validation  

---

## 🏗️ Architecture Overview

The system processes files based on their type and applies specific transformations:

- `Main.java` → Entry point, directory traversal and routing logic  
- `Fiche1_fiche2.java` → Text to structured XML transformation  
- `Sortie1_sortie2.java` → XML normalization (TEI-like format)  
- `Neruda.java` → Text parsing and XML generation (poetry structure)  
- `Renault.java` → HTML data extraction and structuring  
- `Javafx.java` → FXML parsing and attribute extraction  

---

## ⚙️ Technologies

- Java  
- XML / DOM Parser  
- File I/O  
- Structured Data Processing  

---

## ▶️ How to Run

### 1. Compile the project
```bash
javac *.java
