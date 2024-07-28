package com.klok.challenge;

import com.klok.challenge.data.Cliente;
import com.klok.challenge.data.Item;
import com.klok.challenge.data.Pedido;

import com.klok.challenge.repository.PedidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.klok.challenge.service.PedidoService;
import com.klok.challenge.service.NotificacaoService;

import java.util.List;

@SpringBootTest
public class PedidoServiceTest {
    // Teste para verificar se o pedido é criado corretamente com itens em estoque

    @Autowired
    private PedidoService pedidoService; // PedidoService -> Classe que será testada

    @MockBean
    private PedidoRepository pedidoRepository; // PedidoRepository -> Dependência de PedidoService

    @MockBean
    private NotificacaoService notificacaoService; // NotificacaoService -> Dependência de PedidoService

    private Cliente cliente;
    private Item item1;
    private Item item2;
    private Item item3;
    private List<Item> itemsEmEstoque;
    private List<Item> itemsForaDeEstoque;
    private List<Item> itemsMisturados;

    @BeforeEach
    public void setUp() {
        // Método usado para inicializar os objetos necessários para os testes
        cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@example.com");
        cliente.setVip(true);

        item1 = new Item();
        item1.setNome("Item 1");
        item1.setPreco(100.0);
        item1.setQuantidade(2);
        item1.setEstoque(10);

        item2 = new Item();
        item2.setNome("Item 2");
        item2.setPreco(50.0);
        item2.setQuantidade(1);
        item2.setEstoque(5);

        item3 = new Item();
        item3.setNome("Item 3");
        item3.setPreco(30.0);
        item3.setQuantidade(1);
        item3.setEstoque(0);

        itemsEmEstoque = List.of(item1, item2);
        itemsForaDeEstoque = List.of(item3);
        itemsMisturados = List.of(item1, item3);

        Mockito.when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCriarPedidoComItensEmEstoque() {
        // Método de teste para verificar se o pedido é criado corretamente com itens em estoque
        Pedido pedido = pedidoService.criarPedido(cliente, itemsEmEstoque);
        assertNotNull(pedido);
        assertTrue(pedido.isEmEstoque());
        assertEquals(250.0, pedido.getTotal());
        assertEquals(225.0, pedido.getTotalComDesconto());
        assertNotNull(pedido.getDataEntrega());
    }

    @Test
    public void testCriarPedidoComItensForaDeEstoque() {
        // Método de teste para verificar se o pedido é criado corretamente com itens fora de estoque
        Pedido pedido = pedidoService.criarPedido(cliente, itemsForaDeEstoque);
        assertNotNull(pedido);
        assertFalse(pedido.isEmEstoque());
        assertEquals(30.0, pedido.getTotal());
        assertEquals(27.0, pedido.getTotalComDesconto());
        assertNull(pedido.getDataEntrega());
    }

    @Test
    public void testCriarPedidoComItensMisturados() {
        // Método de teste para verificar se o pedido é criado corretamente com
        // itens misturados (com e sem itens em estoque)
        Pedido pedido = pedidoService.criarPedido(cliente, itemsMisturados);
        assertNotNull(pedido);
        assertFalse(pedido.isEmEstoque());
        assertEquals(230.0, pedido.getTotal());
        assertEquals(207.0, pedido.getTotalComDesconto());
        assertNull(pedido.getDataEntrega());
    }
}
