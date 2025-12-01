package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.RegionResponse;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.service.shared.skill.SkillService;
import com.hw.hwjobbackend.util.PaginationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/common/skills")
public class SkillController {

    SkillService skillService;

    @GetMapping
    public ApiResponse<List<SkillResponse>> getAllSkills(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<SkillResponse> response = skillService.getAllSkills(zeroBasedPage, size);
            return ApiResponse.<List<SkillResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<SkillResponse>>builder()
                .result(skillService.getAllSkills())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SkillResponse> getRegionById(@PathVariable Long id) {
        return ApiResponse.<SkillResponse>builder()
                .result(skillService.getSkillById(id))
                .build();
    }
}
