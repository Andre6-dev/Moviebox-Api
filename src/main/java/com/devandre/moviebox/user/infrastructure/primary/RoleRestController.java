package com.devandre.moviebox.user.infrastructure.primary;

import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import com.devandre.moviebox.user.application.port.in.query.GetRoleUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    private final GetRoleUseCase getRoleUseCase;

    public RoleRestController(GetRoleUseCase getRoleUseCase) {
        this.getRoleUseCase = getRoleUseCase;
    }

    @GetMapping()
    public ResponseEntity<Object> getRoles() {
        return ResponseHandler.generateResponse(HttpStatus.OK, getRoleUseCase.getRoles(), true);
    }
}
