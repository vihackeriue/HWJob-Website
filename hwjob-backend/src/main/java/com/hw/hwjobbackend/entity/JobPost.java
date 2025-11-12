package com.hw.hwjobbackend.entity;


import com.hw.hwjobbackend.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;


@Entity(name = "job-posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    Recruiter recruiter;

    @ManyToOne
    Level level;

    String title;

    String description;

    int quantity;

    String requirements;

    String salaryRange;

    PostStatus postStatus;

    LocalDateTime endedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    Ward ward;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;

}
