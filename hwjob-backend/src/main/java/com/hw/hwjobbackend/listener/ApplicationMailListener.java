package com.hw.hwjobbackend.listener;

import com.hw.hwjobbackend.event.ApplicationStatusChangedEvent;
import com.hw.hwjobbackend.service.email.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationMailListener {

    EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    @EventListener
    @Async
    public void sendStatusMail(ApplicationStatusChangedEvent event) {

        String subject = "[HWJOB - " + event.jobTitle() + "] Cập nhật trạng thái hồ sơ ứng tuyển";

        // HTML template full: Header - Body - Footer
        String content;
        String websiteUrl = "http://localhost:5173/";

        switch (event.status()) {
            case APPROVED -> content = """
                <html>
                <body style="font-family: Arial, sans-serif; color:#333; margin:0; padding:0;">
                  <!-- HEADER -->
                  <div style="background-color: #4CAF50; padding: 20px; color: white;">
                    <h1 style="margin:0;">HWJOB - Thông báo hồ sơ ứng tuyển</h1>
                  </div>

                  <!-- BODY -->
                  <div style="padding: 20px;">
                    <h2 style="color: #4CAF50;">Chúc mừng!</h2>
                    <p>Hồ sơ ứng tuyển của bạn cho vị trí <strong>%s</strong> đã được <strong>duyệt</strong>.</p>
                    <p>Vui lòng truy cập địa chỉ website tuyển dụng để làm việc: 
                       <a href="%s" target="_blank">%s</a>
                    </p>
                  </div>

                  <!-- FOOTER -->
                  <div style="padding: 20px; font-size: 12px; color: #999; border-top: 1px solid #eee;">
                    <p>Đây là email tự động từ HWJOB, vui lòng không trả lời.</p>
                    <p>© 2026 HWJOB. All rights reserved.</p>
                  </div>
                </body>
                </html>
            """.formatted(event.jobTitle(), websiteUrl, websiteUrl);

            case REJECTED -> content = """
                <html>
                <body style="font-family: Arial, sans-serif; color:#333; margin:0; padding:0;">
                  <!-- HEADER -->
                  <div style="background-color: #F44336; padding: 20px; color: white;">
                    <h1 style="margin:0;">HWJOB - Thông báo hồ sơ ứng tuyển</h1>
                  </div>

                  <!-- BODY -->
                  <div style="padding: 20px;">
                    <h2 style="color: #F44336;">Rất tiếc!</h2>
                    <p>Hồ sơ ứng tuyển của bạn cho vị trí <strong>%s</strong> <strong>chưa được chọn</strong>.</p>
                    <p>Vui lòng truy cập địa chỉ website tuyển dụng để tham khảo các vị trí khác: 
                       <a href="%s" target="_blank">%s</a>
                    </p>
                  </div>

                  <!-- FOOTER -->
                  <div style="padding: 20px; font-size: 12px; color: #999; border-top: 1px solid #eee;">
                    <p>Đây là email tự động từ HWJOB, vui lòng không trả lời.</p>
                    <p>© 2026 HWJOB. All rights reserved.</p>
                  </div>
                </body>
                </html>
            """.formatted(event.jobTitle(), websiteUrl, websiteUrl);

            default -> content = """
                <html>
                <body style="font-family: Arial, sans-serif; color:#333; margin:0; padding:0;">
                  <!-- HEADER -->
                  <div style="background-color: #2196F3; padding: 20px; color: white;">
                    <h1 style="margin:0;">HWJOB - Thông báo hồ sơ ứng tuyển</h1>
                  </div>

                  <!-- BODY -->
                  <div style="padding: 20px;">
                    <p>Hồ sơ ứng tuyển của bạn cho vị trí <strong>%s</strong> đã được cập nhật trạng thái: <strong>%s</strong>.</p>
                    <p>Vui lòng truy cập địa chỉ website tuyển dụng để kiểm tra chi tiết: 
                       <a href="%s" target="_blank">%s</a>
                    </p>
                  </div>

                  <!-- FOOTER -->
                  <div style="padding: 20px; font-size: 12px; color: #999; border-top: 1px solid #eee;">
                    <p>Đây là email tự động từ HWJOB, vui lòng không trả lời.</p>
                    <p>© 2026 HWJOB. All rights reserved.</p>
                  </div>
                </body>
                </html>
            """.formatted(event.jobTitle(), event.status(), websiteUrl, websiteUrl);
        }

        emailService.sendEmail(event.email(), subject, content);
    }

}
