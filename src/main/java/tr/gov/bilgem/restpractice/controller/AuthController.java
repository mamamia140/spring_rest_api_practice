package tr.gov.bilgem.restpractice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.gov.bilgem.restpractice.misc.JwtUtil;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        try {
            // Validate Authorization header format
            if (!authHeader.startsWith("Basic ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid authorization header format"));
            }

            // Decode Basic Auth header
            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));

            String[] parts = credentials.split(":", 2);
            if (parts.length != 2) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials format"));
            }

            String username = parts[0];
            String password = parts[1];

            // Authenticate user
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // Generate JWT token
            String token = jwtUtil.generateToken(username);

            return ResponseEntity.ok(Map.of(
                    "access_token", token,
                    "token_type", "bearer",
                    "expires_in", 3600
            ));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request: " + ex.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            // Validate Authorization header format
            if (!authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid authorization header format"));
            }

            // Extract token from header
            String token = authHeader.substring(7);

            // Validate token
            if (!jwtUtil.isTokenValid(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid or expired token"));
            }

            // Clear security context (for current request)
            SecurityContextHolder.clearContext();

            // Since we're using stateless JWT tokens, logout is primarily handled client-side
            // The client should discard the token after receiving this response
            return ResponseEntity.ok(Map.of(
                    "message", "Successfully logged out",
                    "timestamp", System.currentTimeMillis()
            ));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request: " + ex.getMessage()));
        }
    }
}