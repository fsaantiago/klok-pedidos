package com.klok.challenge.service;

import com.klok.challenge.data.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    public void verificarEstoque(List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("A lista de itens não pode ser nula");
        }

        boolean todosEmEstoque = items.stream().allMatch(item -> item.getQuantidade() <= item.getEstoque());
        if (!todosEmEstoque) {
            throw new IllegalArgumentException("Um ou mais itens não estão em estoque");
        }
    }
}
