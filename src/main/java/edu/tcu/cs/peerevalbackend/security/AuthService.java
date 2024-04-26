package edu.tcu.cs.peerevalbackend.security;

import edu.tcu.cs.peerevalbackend.user.MyUserPrincipal;
import edu.tcu.cs.peerevalbackend.user.PeerEvalUser;
import edu.tcu.cs.peerevalbackend.user.converter.UserToUserDtoConverter;
import edu.tcu.cs.peerevalbackend.user.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userToUserDtoConverter;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // create user info
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        PeerEvalUser user = principal.getUser();
        UserDto userDto = this.userToUserDtoConverter.convert(user);

        // create a JWT
        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);
        return loginResultMap;
    }
}
