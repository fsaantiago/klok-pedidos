package com.klok.challenge.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {
    @Override
    public void enviarNotificacao(String email, String mensagem, String assunto) {
        System.out.println("Enviando e-mail para " + email + ": " + mensagem + " com o assunto: " + assunto);
    }
}
