package com.klok.challenge;

import com.klok.challenge.security.JwtTokenUtil;
import com.klok.challenge.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    //Teste usado para verificar se a autenticação e a autorização estão funcionando corretamente

    @Autowired
    private MockMvc mockMvc;
    // MockMvc -> Classe usada para simular requisições HTTP

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    // JwtTokenUtil -> Classe usada para gerar tokens JWT

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    // UserDetailsServiceImpl -> Classe usada para carregar detalhes do usuário

    @Test
    public void testAuthenticate() throws Exception {
        //Teste com informações mockadas para verificar se a autenticação está funcionando corretamente
        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"user\", \"password\": \"password\" }"))
                        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testProtectedEndpoint() throws Exception {
        //Teste com informações mockadas para verificar se o endpoint protegido está funcionando corretamente
        String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername("user"));

        mockMvc.perform(post("/api/pedidos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"cliente\": { \"nome\": \"João\", \"email\": \"joao@example.com\", \"vip\": true }, \"items\": [ { \"nome\": \"Item 1\", \"preco\": 100.0, \"quantidade\": 2, \"estoque\": 10 }, { \"nome\": \"Item 2\", \"preco\": 50.0, \"quantidade\": 1, \"estoque\": 5 } ] }"))
                        .andExpect(status().isOk());
    }
}
