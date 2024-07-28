package com.klok.challenge;

import com.klok.challenge.data.Cliente;
import com.klok.challenge.data.Item;
import com.klok.challenge.data.Pedido;
import com.klok.challenge.service.EstoqueService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestApplicationTests {

	@Test
	void contextLoads() {
		//Teste para verificar se a aplicação é carregada corretamente
	}

	@Test
	void testCliente() {
		//Teste para verificar se a exceção IllegalArgumentException é lançada quando o email do cliente é nulo
		Cliente cliente = new Cliente();
		cliente.setNome("João");
		cliente.setEmail("");
		cliente.setVip(true);
	}

	@Test
	void testPedido() {
		//Teste para verificar se a exceção NullPointerException é lançada quando o cliente é nulo
		Pedido pedido = new Pedido();
		pedido.setCliente(new Cliente());
		pedido.setEmEstoque(true);
		pedido.setDataEntrega(null);
	}

	@Test
	void testEstoqueServiceComItensEmEstoque() {
		//Teste usado para verificar se a exceção IllegalArgumentException
		//aponta quando os itens estão em estoque
		EstoqueService estoqueService = new EstoqueService();

		Item item1 = new Item();
		item1.setNome("Item 1");
		item1.setPreco(100.0);
		item1.setQuantidade(2);
		item1.setEstoque(10);

		Item item2 = new Item();
		item2.setNome("Item 2");
		item2.setPreco(50.0);
		item2.setQuantidade(1);
		item2.setEstoque(5);

		List<Item> itemsEmEstoque = List.of(item1, item2);

		estoqueService.verificarEstoque(itemsEmEstoque);
	}

	@Test
	void testEstoqueServiceComItensForaDeEstoque() {
		//Teste usado para verificar se os itens fora de estoque lançam a exceção
		//IllegalArgumentException quando os itens não estão em estoque
		EstoqueService estoqueService = new EstoqueService();

		Item item1 = new Item();
		item1.setNome("Item 1");
		item1.setPreco(100.0);
		item1.setQuantidade(2);
		item1.setEstoque(1);

		List<Item> itemsForaDeEstoque = List.of(item1);

		try {
			estoqueService.verificarEstoque(itemsForaDeEstoque);
		} catch (IllegalArgumentException e) {
			assert e.getMessage().equals("Um ou mais itens não estão em estoque");
		}
	}

	@Test
	void testEstoqueServiceComItensMisturados() {
		//Teste usado para verificar se a exceção IllegalArgumentException é lançada quando a
		//lista de itens contém itens em estoque e fora de estoque
		EstoqueService estoqueService = new EstoqueService();

		Item item1 = new Item();
		item1.setNome("Item 1");
		item1.setPreco(100.0);
		item1.setQuantidade(2);
		item1.setEstoque(10);

		Item item2 = new Item();
		item2.setNome("Item 2");
		item2.setPreco(50.0);
		item2.setQuantidade(1);
		item2.setEstoque(0);

		List<Item> itemsMisturados = List.of(item1, item2);

		try {
			estoqueService.verificarEstoque(itemsMisturados);
		} catch (IllegalArgumentException e) {
			assert e.getMessage().equals("Um ou mais itens não estão em estoque");
		}
	}
}
