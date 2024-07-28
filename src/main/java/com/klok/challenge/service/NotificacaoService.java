package com.klok.challenge.service;

public interface NotificacaoService {
    void enviarNotificacao(String email, String mensagem, String assunto);
}
