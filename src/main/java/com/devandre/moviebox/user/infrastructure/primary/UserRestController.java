package com.devandre.moviebox.user.infrastructure.primary;

import com.devandre.moviebox.user.application.service.UserApplicationService;
import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserApplicationService userApplicationService;

    public UserRestController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping("/find-one")
    public ResponseEntity<User> findOne(@RequestParam UUID publicId) {
        return ResponseEntity.ok(userApplicationService.getUser(new UserPublicId(publicId)));
    }

    @GetMapping("/find-one-by-email")
    public ResponseEntity<User> findOneByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userApplicationService.getUserByEmail(new UserEmail(email)));
    }
}
