package com.klok.challenge.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    //Classe usada para receber o username e password do usuário para autenticação
    private String username;
    private String password;
}
