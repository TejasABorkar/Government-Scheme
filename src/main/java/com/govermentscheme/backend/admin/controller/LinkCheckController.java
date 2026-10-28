package com.govermentscheme.backend.admin.controller;

import com.govermentscheme.backend.admin.dto.BrokenLinkResponse;
import com.govermentscheme.backend.admin.service.LinkCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/links")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LinkCheckController {

    private final LinkCheckService linkCheckService;

    @PostMapping("/check")
    public ResponseEntity<List<BrokenLinkResponse>> checkAll() {
        return ResponseEntity.ok(linkCheckService.checkAllLinks());
    }

    @GetMapping("/broken")
    public ResponseEntity<List<BrokenLinkResponse>> broken() {
        return ResponseEntity.ok(linkCheckService.getBrokenLinks());
    }
}