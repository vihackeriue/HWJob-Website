package com.hw.hwjobbackend.model.enums.data;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndustryEnum {

    // I. CÔNG NGHỆ THÔNG TIN
    COMPUTER_SCIENCE("Khoa học máy tính", "Nghiên cứu về cấu trúc, thuật toán và hệ thống máy tính."),
    SOFTWARE_ENGINEERING("Công nghệ phần mềm", "Phát triển, thiết kế và bảo trì phần mềm."),
    COMPUTER_NETWORKS("Mạng máy tính và truyền thông dữ liệu", "Tập trung vào hạ tầng và bảo mật mạng."),
    CYBER_SECURITY("An ninh mạng", "Bảo vệ hệ thống thông tin khỏi tấn công và truy cập trái phép."),
    INFORMATION_SYSTEMS("Hệ thống thông tin quản lý", "Ứng dụng CNTT trong quản trị và vận hành doanh nghiệp."),
    DATABASE_TECHNOLOGY("Cơ sở dữ liệu", "Thiết kế, quản lý và tối ưu hóa dữ liệu."),
    WEB_TECHNOLOGY("Công nghệ web", "Xây dựng và phát triển ứng dụng web."),
    ARTIFICIAL_INTELLIGENCE("Trí tuệ nhân tạo", "Nghiên cứu mô phỏng trí thông minh con người."),
    INTERNET_OF_THINGS("Internet vạn vật (IoT)", "Kết nối và quản lý các thiết bị thông minh."),
    VR_AR_TECHNOLOGY("Thực tế ảo và tăng cường", "Ứng dụng công nghệ VR/AR trong học tập và sản xuất."),

    // II. KINH DOANH
    BUSINESS_ADMINISTRATION("Quản trị kinh doanh", "Nghiên cứu quản lý, vận hành và phát triển doanh nghiệp."),
    TOURISM_MANAGEMENT("Quản trị dịch vụ du lịch và lữ hành", "Tổ chức, điều hành và quản lý dịch vụ du lịch."),
    HOTEL_MANAGEMENT("Quản trị khách sạn", "Quản lý hoạt động khách sạn và dịch vụ lưu trú."),
    MARKETING("Marketing", "Nghiên cứu thị trường và chiến lược tiếp thị."),
    REAL_ESTATE("Bất động sản", "Kinh doanh, đầu tư và quản lý tài sản bất động sản."),
    INTERNATIONAL_BUSINESS("Kinh doanh quốc tế", "Giao thương và đầu tư xuyên biên giới."),
    ACCOUNTING("Kế toán", "Ghi chép, phân tích và báo cáo tài chính."),
    AUDITING("Kiểm toán", "Kiểm tra và đánh giá tính minh bạch của báo cáo tài chính."),
    HUMAN_RESOURCE_MANAGEMENT("Quản trị nhân lực", "Tuyển dụng, đào tạo và phát triển con người."),
    MANAGEMENT_INFORMATION_SYSTEMS("Hệ thống thông tin quản lý", "Ứng dụng CNTT hỗ trợ hoạt động quản trị."),
    OFFICE_MANAGEMENT("Quản trị văn phòng", "Tổ chức và điều hành công việc hành chính."),
    ECONOMICS("Kinh tế", "Phân tích và dự báo hoạt động kinh tế."),

    // III. KIẾN TRÚC VÀ XÂY DỰNG
    URBAN_PLANNING("Quy hoạch đô thị", "Thiết kế và tổ chức không gian đô thị."),
    ARCHITECTURE("Kiến trúc công trình", "Thiết kế công trình nhà ở, công cộng, kỹ thuật."),
    CIVIL_ENGINEERING("Kỹ thuật công trình", "Thiết kế và xây dựng hạ tầng kỹ thuật."),
    BRIDGE_ROAD_ENGINEERING("Xây dựng cầu đường", "Thiết kế và thi công cầu đường giao thông."),
    CONSTRUCTION_MATERIALS("Vật liệu và cấu kiện xây dựng", "Nghiên cứu vật liệu sử dụng trong xây dựng."),
    MARINE_ENGINEERING("Xây dựng cảng - công trình biển", "Thiết kế và xây dựng công trình trên biển."),
    HYDRO_ENGINEERING("Thủy lợi - thủy điện và cấp thoát nước", "Quản lý tài nguyên nước và công trình thủy."),
    INTERIOR_DESIGN("Thiết kế nội thất", "Tạo không gian sống và làm việc thẩm mỹ."),
    INDUSTRIAL_DESIGN("Thiết kế công nghiệp", "Thiết kế sản phẩm và quy trình công nghiệp."),

    // IV. LUẬT – NHÂN VĂN
    ECONOMIC_LAW("Luật Kinh tế", "Pháp luật trong lĩnh vực kinh doanh."),
    INTERNATIONAL_LAW("Luật Quốc tế", "Pháp luật quốc tế và quan hệ giữa các quốc gia."),
    LAW("Luật học", "Nghiên cứu hệ thống pháp luật nói chung."),
    KOREAN_STUDIES("Hàn Quốc học", "Văn hóa, ngôn ngữ và lịch sử Hàn Quốc."),
    JAPANESE_STUDIES("Nhật Bản học", "Văn hóa, ngôn ngữ và kinh tế Nhật Bản."),
    CHINESE_STUDIES("Trung Quốc học", "Văn hóa, ngôn ngữ và xã hội Trung Quốc."),
    PHILOSOPHY("Triết học", "Nghiên cứu bản chất và quy luật của tồn tại."),
    CULTURAL_STUDIES("Văn hóa học", "Nghiên cứu về giá trị và bản sắc văn hóa."),
    HISTORY("Lịch sử học", "Nghiên cứu và phân tích quá khứ."),
    LITERATURE("Ngôn ngữ và văn học", "Ngôn ngữ học, biên dịch, sáng tác văn học."),

    // V. BÁO CHÍ VÀ TRUYỀN THÔNG
    JOURNALISM("Báo chí", "Thu thập, xử lý và truyền tải thông tin."),
    MULTIMEDIA_COMMUNICATION("Truyền thông đa phương tiện", "Kết hợp công nghệ và nội dung truyền thông."),
    MEDIA_TECHNOLOGY("Công nghệ truyền thông", "Ứng dụng kỹ thuật số trong truyền thông."),
    PUBLIC_RELATIONS("Quan hệ công chúng", "Xây dựng hình ảnh và quản lý truyền thông công chúng."),

    // VI. KHOA HỌC CƠ BẢN
    BIOTECHNOLOGY("Công nghệ sinh học", "Ứng dụng sinh học vào sản xuất và đời sống."),
    BIOLOGY("Sinh học", "Nghiên cứu sự sống và các quá trình sinh học."),
    BIOENGINEERING("Kỹ thuật sinh học", "Ứng dụng kỹ thuật trong nghiên cứu sinh học."),
    ASTRONOMY("Thiên văn học", "Nghiên cứu vũ trụ và các thiên thể."),
    PHYSICS("Vật lý học", "Nghiên cứu vật chất, năng lượng và quy luật tự nhiên."),
    MATHEMATICS("Toán học", "Cơ sở của khoa học tính toán và mô hình hóa."),
    STATISTICS("Thống kê học", "Phân tích và dự đoán dữ liệu."),

    // VII. SƯ PHẠM
    PRIMARY_EDUCATION("Giáo dục Tiểu học", "Đào tạo giáo viên bậc tiểu học."),
    PRESCHOOL_EDUCATION("Giáo dục Mầm non", "Đào tạo giáo viên mầm non."),
    POLITICAL_EDUCATION("Giáo dục Chính trị", "Giáo dục về chính trị và pháp luật."),
    PHYSICAL_EDUCATION("Giáo dục Thể chất", "Đào tạo giáo viên thể dục."),
    MATH_EDUCATION("Sư phạm Toán học", "Giảng dạy môn Toán học."),
    IT_EDUCATION("Sư phạm Tin học", "Giảng dạy môn Tin học."),
    PHYSICS_EDUCATION("Sư phạm Vật lý", "Giảng dạy môn Vật lý."),
    CHEMISTRY_EDUCATION("Sư phạm Hóa học", "Giảng dạy môn Hóa học."),
    LITERATURE_EDUCATION("Sư phạm Ngữ văn", "Giảng dạy Ngữ văn."),
    HISTORY_EDUCATION("Sư phạm Lịch sử", "Giảng dạy Lịch sử."),
    GEOGRAPHY_EDUCATION("Sư phạm Địa lý", "Giảng dạy Địa lý."),
    ENGLISH_EDUCATION("Sư phạm Tiếng Anh", "Giảng dạy Tiếng Anh."),
    EDUCATIONAL_MANAGEMENT("Quản lý giáo dục", "Tổ chức và quản lý hệ thống giáo dục."),

    // VIII. NÔNG - LÂM - NGƯ NGHIỆP
    AGRICULTURE("Nông nghiệp", "Nghiên cứu và phát triển sản xuất nông nghiệp."),
    AGRICULTURAL_ECONOMICS("Kinh tế nông nghiệp", "Phát triển kinh tế và chính sách nông nghiệp."),
    ANIMAL_HUSBANDRY("Chăn nuôi", "Chăm sóc và phát triển vật nuôi."),
    CROP_SCIENCE("Khoa học cây trồng", "Nghiên cứu và cải tiến giống cây."),
    AGRICULTURAL_BUSINESS("Kinh doanh nông nghiệp", "Kết hợp quản trị và nông nghiệp."),

    // IX. SẢN XUẤT VÀ CHẾ BIẾN
    FOOD_TECHNOLOGY("Công nghệ thực phẩm", "Chế biến, bảo quản và kiểm định thực phẩm."),
    TEXTILE_TECHNOLOGY("Công nghệ dệt", "Kỹ thuật sản xuất và thiết kế dệt may."),
    FOOTWEAR_TECHNOLOGY("Công nghệ da giày", "Thiết kế và sản xuất sản phẩm da giày."),
    WOOD_PROCESSING("Công nghệ chế biến lâm sản", "Chế biến và bảo quản gỗ, sản phẩm từ rừng."),

    // X. SỨC KHỎE
    GENERAL_MEDICINE("Y đa khoa", "Khám và điều trị tổng quát."),
    TRADITIONAL_MEDICINE("Y học cổ truyền", "Kết hợp Đông y trong điều trị."),
    NURSING("Điều dưỡng", "Chăm sóc và hỗ trợ bệnh nhân."),
    DENTISTRY("Răng hàm mặt", "Chẩn đoán và điều trị răng miệng."),
    PHARMACY("Dược học", "Nghiên cứu và sản xuất thuốc."),
    PUBLIC_HEALTH("Y tế công cộng", "Phòng bệnh và nâng cao sức khỏe cộng đồng."),
    MIDWIFERY("Hộ sinh", "Chăm sóc và hỗ trợ sản phụ."),

    // XI. KỸ THUẬT
    MECHANICAL_ENGINEERING("Kỹ thuật cơ khí", "Thiết kế, sản xuất và vận hành máy móc."),
    ELECTRICAL_ENGINEERING("Kỹ thuật điện", "Nghiên cứu và ứng dụng năng lượng điện."),
    AUTOMATION_ENGINEERING("Kỹ thuật điều khiển và tự động hóa", "Ứng dụng robot và tự động hóa trong sản xuất."),
    ELECTRONICS_TELECOMMUNICATIONS("Kỹ thuật điện tử - viễn thông", "Truyền thông và công nghệ tín hiệu."),
    MECHATRONICS_ENGINEERING("Cơ - điện tử", "Kết hợp cơ khí, điện tử và tin học."),
    ENERGY_ENGINEERING("Kỹ thuật năng lượng", "Sản xuất và quản lý năng lượng."),
    AUTOMOTIVE_ENGINEERING("Công nghệ kỹ thuật ô tô", "Thiết kế và bảo trì phương tiện giao thông."),
    ROBOTICS_ENGINEERING("Kỹ thuật Robot", "Nghiên cứu, chế tạo và ứng dụng robot.");


    private final String name;
    private final String description;
}
