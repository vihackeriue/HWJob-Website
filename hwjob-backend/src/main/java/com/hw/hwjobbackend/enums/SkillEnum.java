package com.hw.hwjobbackend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkillEnum {
    // --- CNTT ---
    JAVA("Java"),
    SPRING_BOOT("Spring Boot"),
    PYTHON("Python"),
    DJANGO("Django"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    REACT("ReactJS"),
    ANGULAR("Angular"),
    NODEJS("NodeJS"),
    FLUTTER("Flutter"),
    DART("Dart"),
    KOTLIN("Kotlin"),
    SWIFT("Swift"),
    SQL("SQL"),
    MONGODB("MongoDB"),
    MYSQL("MySQL"),
    POSTGRESQL("PostgreSQL"),
    AWS("AWS"),
    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    GIT("Git"),
    DEVOPS("DevOps"),
    UI_UX("UI/UX Design"),
    FIGMA("Figma"),
    CYBER_SECURITY("Cyber Security"),
    DATA_ANALYSIS("Data Analysis"),
    MACHINE_LEARNING("Machine Learning"),
    AI("Artificial Intelligence"),
    CLOUD_COMPUTING("Cloud Computing"),

    // --- Kinh tế / Văn phòng ---
    ACCOUNTING("Accounting"),
    FINANCE("Finance"),
    SALES("Sales"),
    MARKETING("Marketing"),
    CUSTOMER_SERVICE("Customer Service"),
    PROJECT_MANAGEMENT("Project Management"),
    DATA_ENTRY("Data Entry"),
    OFFICE_ADMIN("Office Administration"),

    // --- Kỹ thuật / Sản xuất ---
    AUTO_CAD("AutoCAD"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    CNC("CNC Machining"),
    QUALITY_CONTROL("Quality Control"),
    SUPPLY_CHAIN("Supply Chain Management"),

    // --- Ngôn ngữ ---
    ENGLISH("English"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    CHINESE("Chinese"),
    VIETNAMESE("Vietnamese");

    private final String name;
}
