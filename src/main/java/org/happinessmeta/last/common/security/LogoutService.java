//package org.happinessmeta.last.common.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.happinessmeta.last.common.exception.TokenNotFoundException;
//import org.happinessmeta.last.token.Token;
////import org.happinessmeta.last.token.TokenRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.stereotype.Service;
//
//@Service // 구현체를 만들어 놓으면, 스프링 컨테이너에서 알아서 구현된 메서드를 주입해준다? 인터페이스는 인스턴스를 가질 수 없다. 형체를 가질 수 있도록 해준다?
//@RequiredArgsConstructor
//public class LogoutService implements LogoutHandler {
//
////    private final TokenRepository tokenRepository;
//
//    @Override
//    public void logout(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    ) {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        jwt = authHeader.substring(7);
//        var storedToken = tokenRepository.findByToken(jwt)
//                .orElse(null);
//        if (storedToken != null) {
//            storedToken.setExpired(true);
//            storedToken.setRevoked(true);
//            tokenRepository.save(storedToken);
//            SecurityContextHolder.clearContext();
//        }
//    }
//}
