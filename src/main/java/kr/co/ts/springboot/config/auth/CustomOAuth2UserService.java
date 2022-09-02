package kr.co.ts.springboot.config.auth;

import kr.co.ts.springboot.config.auth.dto.OAuthAttributes;
import kr.co.ts.springboot.config.auth.dto.SessionUser;
import kr.co.ts.springboot.domain.user.User;
import kr.co.ts.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private final HttpSession session;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attribute = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attribute);

        session.setAttribute("user",new SessionUser(user));



        return new DefaultOAuth2User( Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attribute.getAttributes(),
                attribute.getNameAttributeKey() );
    }

    private User saveOrUpdate( OAuthAttributes attribute ){
        User user = userRepository.findByEmail(attribute.getEmail())
                .map(entity -> entity.update(attribute.getName(), attribute.getPicture()))
                .orElse(attribute.toEntity());

        return userRepository.save(user);

    }


}
