package bankapp.auth;

import bankapp.config.JwtService;
import bankapp.user.User;
import bankapp.user.Role;
import bankapp.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
  }

  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    var user = repository.findByUsername(request.getUsername()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String username;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    username = jwtService.extractUsername(refreshToken);
    if (username != null) {
      var user = this.repository.findByUsername(username).orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);
        var authResponse = AuthResponse.builder().accessToken(accessToken).refreshToken(newRefreshToken).build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
