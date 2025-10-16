package com.hw.hwjobbackend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndustryEnum {
    // --- Các ngành gốc ---
    IT("Công nghệ thông tin", "Ngành phần mềm, phần cứng, dữ liệu, AI", null),
    FINANCE("Kinh tế - Tài chính", "Đầu tư, ngân hàng, bảo hiểm, kế toán", null),
    CONSTRUCTION("Xây dựng", "Thiết kế, thi công, giám sát công trình", null),
    HEALTH("Y tế - Dược", "Chăm sóc sức khỏe, bệnh viện, dược phẩm", null),
    EDUCATION("Giáo dục - Đào tạo", "Giảng dạy, đào tạo, quản lý giáo dục", null),
    MARKETING("Marketing - Truyền thông", "Quảng bá thương hiệu, sáng tạo nội dung", null),
    ENGINEERING("Kỹ thuật", "Các ngành kỹ thuật: điện, cơ khí, hóa học, môi trường...", null),
    MANUFACTURING("Sản xuất & Công nghiệp", "Sản xuất hàng hóa, chế biến, công nghiệp chế tạo", null),
    LOGISTICS("Logistics & Vận tải", "Kho vận, vận chuyển, chuỗi cung ứng", null),
    AGRICULTURE("Nông – Lâm – Thủy sản", "Trồng trọt, chăn nuôi, nuôi trồng thủy sản", null),
    TOURISM("Du lịch, Nhà hàng & Khách sạn", "Dịch vụ du lịch, khách sạn, lưu trú và ẩm thực", null),
    ARTS_DESIGN("Nghệ thuật & Thiết kế", "Thiết kế, mỹ thuật, thời trang, nội thất", null),
    MEDIA("Truyền hình & Phát thanh & Báo chí", "Báo chí, truyền thông, phát thanh, truyền hình", null),
    LEGAL("Pháp lý & Luật", "Tư vấn pháp lý, luật sư, hợp đồng", null),
    IT_HARDWARE("Phần cứng & Thiết bị", null, IT),
    IT_SOFTWARE("Phát triển phần mềm", null, IT),
    IT_INFRA("Hạ tầng & Mạng", null, IT),
    IT_AI("Trí tuệ nhân tạo & Dữ liệu", null, IT),
    IT_CYBERSECURITY("An ninh mạng", null, IT),

    FIN_ACCOUNTING("Kế toán - Kiểm toán", null, FINANCE),
    FIN_BANKING("Ngân hàng - Bảo hiểm", null, FINANCE),
    FIN_INVESTMENT("Đầu tư & Chứng khoán", null, FINANCE),

    CONST_ARCHITECTURE("Kiến trúc – Nội thất", null, CONSTRUCTION),
    CONST_CIVIL("Xây dựng dân dụng & công trình giao thông", null, CONSTRUCTION),
    CONST_MEP("Cơ điện – Điện nước (MEP)", null, CONSTRUCTION),

    HEALTH_DOCTOR("Bác sĩ – Y khoa", null, HEALTH),
    HEALTH_NURSING("Nghề điều dưỡng", null, HEALTH),
    HEALTH_PHARMACY("Dược phẩm", null, HEALTH),
    HEALTH_LAB("Xét nghiệm & Chẩn đoán hình ảnh", null, HEALTH),

    EDUC_TEACHING("Giáo viên phổ thông", null, EDUCATION),
    EDUC_HIGHER("Giáo dục đại học", null, EDUCATION),
    EDUC_TRAINING("Đào tạo nghề & kỹ năng", null, EDUCATION),

    MARK_DIGITAL("Digital Marketing", null, MARKETING),
    MARK_CONTENT("Content Creator / Copywriting", null, MARKETING),

    ENG_ELECTRICAL("Kỹ thuật điện – điện tử", null, ENGINEERING),
    ENG_MECHANICAL("Kỹ thuật cơ khí", null, ENGINEERING),
    ENG_CHEMICAL("Kỹ thuật hóa học", null, ENGINEERING),
    ENG_ENVIRONMENT("Kỹ thuật môi trường", null, ENGINEERING),

    MANUF_FOOD("Chế biến thực phẩm", null, MANUFACTURING),
    MANUF_TEXTILE("Dệt may", null, MANUFACTURING),
    MANUF_ELECTRONICS("Điện tử – Linh kiện", null, MANUFACTURING),
    MANUF_PLASTICS("Nhựa & Hóa chất", null, MANUFACTURING),

    LOG_SUPPLY_CHAIN("Quản lý chuỗi cung ứng", null, LOGISTICS),
    LOG_TRANSPORT("Vận tải – Giao thông", null, LOGISTICS),
    LOG_WAREHOUSE("Kho vận & Logistics Tổng hợp", null, LOGISTICS),

    AGRI_CROP("Trồng trọt", null, AGRICULTURE),
    AGRI_LIVESTOCK("Chăn nuôi", null, AGRICULTURE),
    AGRI_AQUATIC("Nuôi trồng thủy sản", null, AGRICULTURE),

    TOUR_HOTEL("Khách sạn & Lưu trú", null, TOURISM),
    TOUR_FOOD_SERVICE("Dịch vụ ăn uống / F&B", null, TOURISM),
    TOUR_TRAVEL("Du lịch & Lữ hành", null, TOURISM),

    ARTDESIGN_FASHION("Thời trang", null, ARTS_DESIGN),
    ARTDESIGN_INTERIOR("Nội thất", null, ARTS_DESIGN),
    ARTDESIGN_GRAPHIC("Thiết kế đồ họa", null, ARTS_DESIGN),

    MEDIA_BROADCAST("Truyền hình & Phát thanh", null, MEDIA),
    MEDIA_JOURNALISM("Báo chí", null, MEDIA),
    MEDIA_FILM("Điện ảnh & Truyền hình – sản xuất phim", null, MEDIA),

    LEGAL_LAWYER("Luật sư / Tư vấn pháp lý", null, LEGAL),
    LEGAL_CORPORATE("Luật doanh nghiệp", null, LEGAL);

    private final String name;
    private final String description;
    private final IndustryEnum parent;
}
