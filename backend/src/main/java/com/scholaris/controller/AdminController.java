package com.scholaris.controller;

import com.scholaris.dto.QueryRequest;
import com.scholaris.dto.QueryResult;
import com.scholaris.service.AdminQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private final AdminQueryService queryService;

    public AdminController(AdminQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/admin")
    public String portal() {
        return "forward:/admin.html";
    }

    @PostMapping("/api/admin/query")
    @ResponseBody
    public ResponseEntity<QueryResult> executeQuery(
            @Valid @RequestBody QueryRequest request,
            Authentication authentication
    ) {
        QueryResult result = queryService.execute(request.sql(), authentication.getName());
        return ResponseEntity.ok(result);
    }
}
