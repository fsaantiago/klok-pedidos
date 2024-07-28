package com.klok.challenge.service;

import com.klok.challenge.data.Pedido;
import com.klok.challenge.data.Item;
import com.klok.challenge.data.Cliente;
import com.klok.challenge.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final NotificacaoService notificacaoService;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(NotificacaoService notificacaoService, PedidoRepository pedidoRepository) {
        this.notificacaoService = notificacaoService;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Cliente cliente, List<Item> items) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItems(items);
        pedido.setTotal(calcularTotal(items));
        pedido.setTotalComDesconto(calcularTotalComDesconto(pedido.getTotal(), cliente.isVip()));
        pedido.setEmEstoque(verificarEstoque(items));
        pedido.setDataEntrega(calcularDataEntrega(pedido.isEmEstoque()));
        notificarCliente(pedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido obterPedido(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElse(null);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    private double calcularTotal(List<Item> items) {
        return items.stream().mapToDouble(item -> item.getPreco() * item.getQuantidade()).sum();
    }

    private double calcularTotalComDesconto(double total, boolean isVip) {
        return isVip ? total * 0.9 : total;
    }

    private boolean verificarEstoque(List<Item> items) {
        return items.stream().allMatch(item -> item.getQuantidade() <= item.getEstoque());
    }

    private LocalDate calcularDataEntrega(boolean emEstoque) {
        return emEstoque ? LocalDate.now().plusDays(3) : null;
    }

    private void notificarCliente(Pedido pedido) {
        String mensagem = pedido.isEmEstoque()
                ? "Seu pedido será entregue em breve."
                : "Um ou mais itens do seu pedido estão fora de estoque.";
        notificacaoService.enviarNotificacao(pedido.getCliente().getEmail(), mensagem, "Status do Pedido");
    }
}
