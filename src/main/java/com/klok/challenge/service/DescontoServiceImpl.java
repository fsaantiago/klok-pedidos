package com.klok.challenge.service;

import org.springframework.stereotype.Service;

@Service
public class DescontoServiceImpl implements DescontoService {
    @Override
    public double calcularDesconto(double total, boolean isVip) {
        return isVip ? total * 0.9 : total;
    }
}
