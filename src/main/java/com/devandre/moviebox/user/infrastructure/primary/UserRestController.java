package com.devandre.moviebox.user.infrastructure.primary;

import com.devandre.moviebox.shared.application.constants.ProjectConstants;
import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import com.devandre.moviebox.user.application.service.UserApplicationService;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ProjectConstants.API_V1_USERS)
public class UserRestController {

    private final UserApplicationService userApplicationService;

    public UserRestController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping("/find-one")
    public ResponseEntity<Object> findOne(@RequestParam UUID publicId) {
        return ResponseHandler.generateResponse(HttpStatus.OK, userApplicationService.getUser(new UserPublicId(publicId)), true);
    }

    @GetMapping("/find-one-by-email")
    public ResponseEntity<Object> findOneByEmail(@RequestParam String email) {
        return ResponseHandler.generateResponse(HttpStatus.OK, userApplicationService.getUserByEmail(email), true);
    }
}
