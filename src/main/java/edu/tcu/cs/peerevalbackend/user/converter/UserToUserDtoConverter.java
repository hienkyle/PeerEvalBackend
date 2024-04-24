package edu.tcu.cs.peerevalbackend.user.converter;

import edu.tcu.cs.peerevalbackend.user.PeerEvalUser;
import edu.tcu.cs.peerevalbackend.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<PeerEvalUser, UserDto> {

    @Override
    public UserDto convert(PeerEvalUser source) {
        UserDto userDto = new UserDto(source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles());
        return userDto;
    }
}
