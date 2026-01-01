package com.hw.hwjobbackend.repository.http_client;


import com.hw.hwjobbackend.model.dto.api.request.CandidateIndexingRequest;
import com.hw.hwjobbackend.model.dto.api.request.JobPostIndexingRequest;
import com.hw.hwjobbackend.model.dto.api.request.JobPostRecommendationRequest;
import com.hw.hwjobbackend.model.dto.api.request.RankCandidateRequest;
import com.hw.hwjobbackend.model.dto.api.response.RecommendationResponse;
import com.hw.hwjobbackend.model.dto.api.response.ServerAIMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "server-ai", url = "${cors.server-api-ai}")
public interface ServerAIFeignClient {

    @GetMapping("/hello")
    ServerAIMessageResponse sendMessage();

    @PostMapping("/index-job")
    ServerAIMessageResponse indexJobPost(@RequestBody JobPostIndexingRequest request);

    @PostMapping("/index-candidate")
    ServerAIMessageResponse indexCandidate(@RequestBody CandidateIndexingRequest request);

    @PostMapping("/recommend-jobs")
    RecommendationResponse recommendJobs(@RequestBody JobPostRecommendationRequest request);

    @PostMapping("/rank-pending-candidates")
    RecommendationResponse rankCandidates(@RequestBody RankCandidateRequest request);
}
