package com.hw.hwjobbackend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelEnum {

    // I. CẤP HỌC TẬP / ĐÀO TẠO
    STUDENT("Sinh viên"),
    INTERN("Thực tập sinh"),
    TRAINEE("Học việc / Đào tạo nghề"),
    FRESH_GRADUATE("Mới tốt nghiệp"),

    // II. CẤP NGHỀ NGHIỆP (KINH NGHIỆM & CHUYÊN MÔN)
    JUNIOR("Nhân viên cấp thấp (Junior)"),
    MID_LEVEL("Nhân viên có kinh nghiệm (Middle)"),
    SENIOR("Chuyên viên cao cấp (Senior)"),
    EXPERT("Chuyên gia (Expert)"),
    TECH_LEAD("Trưởng nhóm kỹ thuật (Tech Lead)"),
    CONSULTANT("Chuyên viên tư vấn (Consultant)"),
    SPECIALIST("Chuyên viên đặc thù / chuyên sâu (Specialist)"),
    RESEARCHER("Nhà nghiên cứu (Researcher)"),
    LECTURER("Giảng viên / Nhà đào tạo (Lecturer)"),

    // III. CẤP QUẢN LÝ / LÃNH ĐẠO
    TEAM_LEADER("Trưởng nhóm (Team Leader)"),
    SUPERVISOR("Giám sát viên (Supervisor)"),
    PROJECT_MANAGER("Quản lý dự án (Project Manager)"),
    PRODUCT_MANAGER("Quản lý sản phẩm (Product Manager)"),
    DEPARTMENT_HEAD("Trưởng phòng / Trưởng bộ phận (Department Head)"),
    MANAGER("Quản lý (Manager)"),
    SENIOR_MANAGER("Quản lý cấp cao (Senior Manager)"),
    DIRECTOR("Giám đốc (Director)"),
    VICE_DIRECTOR("Phó giám đốc (Vice Director)"),
    CEO("Tổng giám đốc (CEO)"),
    CTO("Giám đốc công nghệ (CTO)"),
    CFO("Giám đốc tài chính (CFO)"),
    CMO("Giám đốc marketing (CMO)"),
    ACADEMIC_DIRECTOR("Giám đốc học thuật / nghiên cứu (Academic Director)"),
    PRINCIPAL("Hiệu trưởng / Giám đốc trung tâm (Principal)"),
    PROFESSOR("Giáo sư / Nhà khoa học (Professor)"),

    // IV. CẤP NGÀNH ĐẶC THÙ KHÁC
    DOCTOR("Bác sĩ (Doctor)"),
    SURGEON("Bác sĩ phẫu thuật (Surgeon)"),
    NURSE("Điều dưỡng (Nurse)"),
    PHARMACIST("Dược sĩ (Pharmacist)"),
    ENGINEER("Kỹ sư (Engineer)"),
    ARCHITECT("Kiến trúc sư (Architect)"),
    DESIGNER("Nhà thiết kế (Designer)"),
    ARTIST("Nghệ sĩ / Nhà sáng tạo (Artist)"),
    WRITER("Nhà văn / Biên tập viên (Writer)"),
    LAWYER("Luật sư (Lawyer)"),
    ACCOUNTANT("Kế toán viên (Accountant)"),
    TEACHER("Giáo viên (Teacher)"),
    SCIENTIST("Nhà khoa học (Scientist)"),
    RECTOR("Hiệu trưởng đại học (Rector)");

    private final String name;
}
