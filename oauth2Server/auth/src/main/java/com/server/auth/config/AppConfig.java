package com.server.auth.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j
public class AppConfig implements CommandLineRunner {

    @Bean
    public UserDetailsService userDetailService(){
        var user = User.withUsername("sathvikt23")
                .password("password")
                .roles("USER","ADMIN")
                .build();
        System.out.println("Creating user: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();

    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        RegisteredClient registeredClient = RegisteredClient.withId(String.valueOf(UUID.randomUUID()))
                .clientId("client")
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8081")
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scope("openid")
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofHours(6))//it is hours, be care full
                                .build()
                )
                .clientSettings(
                        ClientSettings.builder()
                                .requireProofKey(true)
                                .build()
                )
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

   @Bean
   public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
   }

   @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
       KeyPairGenerator generator =KeyPairGenerator.getInstance("RSA");
       generator.initialize(2048);
       KeyPair keyPair=generator.generateKeyPair();

       RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();
       RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();

       RSAKey rsaKey = new RSAKey.Builder(publicKey)
               .privateKey(privateKey)
               .keyID(String.valueOf(UUID.randomUUID()))
               .build();
       return new ImmutableJWKSet<>(new JWKSet(rsaKey));

   }
//CORS
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(
//                List.of("http://localhost:3000")
//        );
//
//        configuration.setAllowedMethods(
//                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
//        );
//
//        configuration.setAllowedHeaders(
//                List.of("*")
//        );
//
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }

   @Override
    public void run (String ...args) throws Exception  {
        byte [] code = new byte[32];
        new SecureRandom().nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        byte [] digestedVerifier = MessageDigest.getInstance("SHA-256").digest(verifier.getBytes());
        String codeChallange = Base64.getUrlEncoder().withoutPadding().encodeToString(digestedVerifier);

        log.info("Challenge Verifier : "+verifier);
        log.info("Code challange "+codeChallange);

   }


}
