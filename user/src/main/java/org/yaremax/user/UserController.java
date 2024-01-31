package org.yaremax.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok()
                .body(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);

        return ResponseEntity.ok()
                .body("User was successfully added to db");
    }
}
