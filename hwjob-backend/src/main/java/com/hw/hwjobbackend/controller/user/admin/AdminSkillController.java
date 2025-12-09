package com.hw.hwjobbackend.controller.user.admin;


import com.hw.hwjobbackend.model.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.service.admin.skill.AdminSkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/skills")
public class AdminSkillController {

    AdminSkillService adminSkillService;

    @PostMapping
    public ApiResponse<SkillResponse> createSkill(
            @RequestBody SkillRequest request) {
        return ApiResponse.<SkillResponse>builder()
                .result(adminSkillService.createSkill(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SkillResponse> updateSkill(
            @PathVariable Long id,
            @RequestBody SkillRequest request
    ) {
        return ApiResponse.<SkillResponse>builder()
                .result(adminSkillService.updateSkill(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSkill(@PathVariable Long id) {
        adminSkillService.deleteSkill(id);
        return ApiResponse.<Void>builder().build();
    }

}
