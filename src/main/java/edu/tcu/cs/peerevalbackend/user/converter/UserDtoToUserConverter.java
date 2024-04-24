package edu.tcu.cs.peerevalbackend.user.converter;

import edu.tcu.cs.peerevalbackend.user.PeerEvalUser;
import edu.tcu.cs.peerevalbackend.user.dto.UserDto;
import org.springframework.core.convert.converter.Converter;

public class UserDtoToUserConverter implements Converter<UserDto, PeerEvalUser> {
    @Override
    public PeerEvalUser convert(UserDto source) {
        PeerEvalUser user = new PeerEvalUser();
        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.
        return null;
    }
}
