package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.request.SkillRequest;
import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.SkillResponse;
import com.hw.hwjobbackend.service.skill.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/skills")
public class SkillController {

    SkillService skillService;

    @GetMapping
    public ApiResponse<List<SkillResponse>> getAllSkill() {
        return ApiResponse.<List<SkillResponse>>builder()
                .result(skillService.getAllSkill())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SkillResponse> getSkillById(@PathVariable Long id) {
        return ApiResponse.<SkillResponse>builder()
                .result(skillService.getSkillById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<SkillResponse> createSkill(@RequestBody SkillRequest request) {
        return ApiResponse.<SkillResponse>builder()
                .result(skillService.createSkill(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SkillResponse> updateSkill(@PathVariable Long id, @RequestBody SkillRequest request) {
        return ApiResponse.<SkillResponse>builder()
                .result(skillService.updateSkill(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ApiResponse.<Void>builder().build();
    }
}
