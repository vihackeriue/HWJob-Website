package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.model.enums.RoleEnum;

import com.hw.hwjobbackend.repository.job_post.JobPostRepository;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostViewServiceImpl implements JobPostViewService {
    RedisTemplate<String, Object> redisTemplate;
    String VIEW_KEY = "job:view:";
    String VIEWED_KEY = "job:viewed:";
    JobPostRepository jobPostRepository;

    @Override
    public void increaseView(String jobPostId, String viewerKey) {
        // LOẠI TRỪ RECRUITER / ADMIN XEM BÀI VIẾT
        if (SecurityUtils.isAuthenticated()) {
            RoleEnum role = SecurityUtils.getCurrentUserRole();
            if (role == RoleEnum.RECRUITER || role == RoleEnum.ADMIN) {
                return;
            }
        }

        String viewedKey = VIEWED_KEY + jobPostId;

        Long added = redisTemplate.opsForSet().add(viewedKey, viewerKey);

        if (added != null && added == 1) {
            redisTemplate.expire(viewedKey, 1, TimeUnit.HOURS);
            redisTemplate.opsForValue().increment(VIEW_KEY + jobPostId);
        }
    }
    @Override
    public Long getRedisView(String jobPostId) {
        Object value = redisTemplate.opsForValue().get(VIEW_KEY + jobPostId);
        return value == null ? 0L : Long.parseLong(value.toString());
    }
    @Override
    public String getViewerKey(HttpServletRequest request) {

        if (SecurityUtils.isAuthenticated()) {
            return "USER_" + SecurityUtils.getCurrentUserId();
        }

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return "IP_" + ip + "_" + (userAgent == null ? "" : userAgent.hashCode());
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    public void syncViewToDb() {

        Set<String> keys = redisTemplate.keys("job:view:*");
        if (keys == null || keys.isEmpty()) return;
        for (String key : keys) {
            Object value = redisTemplate.opsForValue().get(key);
            if (value == null) continue;

            Long views = Long.parseLong(value.toString());

            String jobPostId = key.replace(VIEW_KEY, "");

            jobPostRepository.increaseViewCount(jobPostId, views);
            redisTemplate.delete(key);
        }
    }
}
