package com.hw.hwjobbackend.service.shared.job_post;

import jakarta.servlet.http.HttpServletRequest;

public interface JobPostViewService {
    void increaseView(String jobId, String viewerKey);
    Long getRedisView(String jobPostId);
    String getViewerKey(HttpServletRequest request);
}
