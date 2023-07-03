package org.example.API;

import lombok.RequiredArgsConstructor;
import org.example.authorizationConfig.JwtUtils;
import org.example.model.User;
import org.example.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    @PostMapping("/authentication")
    public ResponseEntity<String> authenticate(@RequestBody User request){
        if(request.getUserName().isEmpty()){
            return null;
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword())
        );
        System.out.println(request.getUserName());System.out.println(request.getPassword());

        User u = userService.selectUserByName(request.getUserName()).get();
        final UserDetails user = new org.springframework.security.core.userdetails.User(
                u.getUserName(),
                u.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        if(userService.verify(u)){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }else {
            return null;
        }
    }
}
