package com.hw.hwjobbackend.model.enums.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkillEnum {

    // I. IT & SOFTWARE DEVELOPMENT
    JAVA("Java", "Phát triển ứng dụng backend và enterprise."),
    SPRING_BOOT("Spring Boot", "Framework phổ biến cho backend Java."),
    JAVASCRIPT("JavaScript", "Ngôn ngữ cho web frontend/backend."),
    TYPESCRIPT("TypeScript", "Phiên bản mở rộng của JavaScript."),
    REACT("ReactJS", "Thư viện frontend cho UI."),
    ANGULAR("Angular", "Framework để xây dựng SPA."),
    VUE("VueJS", "Framework frontend linh hoạt."),
    NODEJS("NodeJS", "Backend sử dụng JavaScript."),
    PYTHON("Python", "Dùng trong backend, AI và automation."),
    DJANGO("Django", "Framework web Python."),
    FLASK("Flask", "Framework web nhẹ cho Python."),
    C_SHARP(".NET/C#", "Phát triển ứng dụng Windows & web."),
    DOTNET("ASP.NET", "Framework backend từ Microsoft."),
    PHP("PHP", "Phát triển website và backend."),
    LARAVEL("Laravel", "Framework PHP phổ biến."),
    MOBILE_ANDROID("Android", "Lập trình ứng dụng Android."),
    MOBILE_IOS("iOS", "Phát triển ứng dụng trên iOS."),
    FULLSTACK("Fullstack", "Kỹ năng làm cả frontend & backend."),

    // DATA, AI & CLOUD
    SQL("SQL", "Truy vấn và quản lý dữ liệu."),
    MYSQL("MySQL", "Hệ quản trị cơ sở dữ liệu phổ biến."),
    POSTGRESQL("PostgreSQL", "Database quan hệ mạnh mẽ."),
    MONGODB("MongoDB", "CSDL NoSQL phổ biến."),
    DATA_ANALYSIS("Data Analysis", "Phân tích và trực quan hóa dữ liệu."),
    MACHINE_LEARNING("Machine Learning", "Xây dựng mô hình học máy."),
    DEEP_LEARNING("Deep Learning", "Mô hình AI nâng cao."),
    COMPUTER_VISION("Computer Vision", "Xử lý ảnh và thị giác máy tính."),
    CLOUD_AWS("AWS Cloud", "Dịch vụ cloud từ Amazon."),
    CLOUD_AZURE("Azure Cloud", "Cloud service từ Microsoft."),
    CLOUD_GCP("GCP", "Cloud service từ Google."),
    DEVOPS("DevOps", "Triển khai và vận hành hệ thống tự động."),
    DOCKER("Docker", "Container và đóng gói ứng dụng."),
    KUBERNETES("Kubernetes", "Quản lý container tối ưu."),

    // NETWORK & SECURITY
    NETWORKING("Networking", "Quản trị và cấu hình hệ thống mạng."),
    CYBER_SECURITY("Cyber Security", "Bảo mật ứng dụng và hệ thống."),
    PENTESTING("Pentesting", "Kiểm thử và khai thác bảo mật."),

    // DESIGN & MULTIMEDIA
    UI_UX("UI/UX Design", "Thiết kế trải nghiệm người dùng."),
    FIGMA("Figma", "Thiết kế prototype giao diện UI."),
    GRAPHIC_DESIGN("Graphic Design", "Thiết kế đồ họa truyền thông."),
    VIDEO_EDITING("Video Editing", "Biên tập và xử lý video."),

    // BUSINESS & MARKETING
    SALES("Sales", "Bán hàng và phát triển khách hàng."),
    MARKETING("Marketing", "Chiến lược truyền thông và tiếp thị."),
    DIGITAL_MARKETING("Digital Marketing", "SEO, Ads và kênh số."),
    SEO("SEO", "Tối ưu công cụ tìm kiếm."),
    CUSTOMER_SERVICE("Customer Service", "Chăm sóc khách hàng."),
    BUSINESS_ANALYSIS("Business Analysis", "Thu thập và phân tích yêu cầu doanh nghiệp."),

    // ACCOUNTING & FINANCE
    ACCOUNTING("Accounting", "Ghi chép và xử lý thông tin tài chính."),
    AUDIT("Audit", "Kiểm toán tài chính."),
    EXCEL("Excel", "Phân tích và xử lý dữ liệu bằng Excel."),

    // CONSTRUCTION & ENGINEERING
    AUTOCAD("AutoCAD", "Thiết kế bản vẽ kỹ thuật."),
    REVIT("Revit", "Thiết kế BIM cho xây dựng."),
    SOLIDWORKS("SolidWorks", "Mô phỏng cơ khí 3D."),

    // HEALTHCARE
    CARE("Nursing Care", "Kỹ năng chăm sóc và điều dưỡng."),
    MEDICAL_ASSIST("Medical Assistant", "Hỗ trợ bác sĩ và y tá."),

    // EDUCATION
    TEACHING("Teaching", "Kỹ năng sư phạm và giảng dạy."),
    CLASS_MANAGEMENT("Class Management", "Quản lý lớp học hiệu quả."),

    // SOFT SKILLS (áp dụng mọi ngành)
    COMMUNICATION("Communication", "Giao tiếp và truyền đạt thông tin."),
    TEAMWORK("Teamwork", "Làm việc nhóm hiệu quả."),
    LEADERSHIP("Leadership", "Dẫn dắt và quản lý nhóm."),
    TIME_MANAGEMENT("Time Management", "Lập kế hoạch và tối ưu thời gian."),
    PROBLEM_SOLVING("Problem Solving", "Phân tích và xử lý vấn đề."),
    CRITICAL_THINKING("Critical Thinking", "Tư duy phản biện và logic."),
    CREATIVITY("Creativity", "Sáng tạo trong giải pháp và ý tưởng.");

    private final String name;
    private final String description;
}
