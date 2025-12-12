package com.hw.hwjobbackend.configuration.web_socket;


import com.hw.hwjobbackend.service.authentication.JwtService;
import com.hw.hwjobbackend.util.TokenUtils;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = TokenUtils.extractToken(authHeader);

                try {
                    if (jwtService.introspect(token)) {
                        SignedJWT signedJWT = SignedJWT.parse(token);
                        String userId = signedJWT.getJWTClaimsSet().getSubject();
                        String scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");

                        List<SimpleGrantedAuthority> authorities = Arrays.stream(scope.split(" "))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userId, null, authorities);

                        accessor.setUser(auth);
                        log.info("WebSocket authenticated for user: {}", userId);
                    } else {
                        log.warn("WebSocket authentication failed: invalid token");
                    }
                } catch (Exception e) {
                    log.error("WebSocket authentication error: {}", e.getMessage());
                }
            } else {
                log.warn("WebSocket connection without Authorization header");
            }
        }

        return message;
    }


}
