package com.fiap.pedidos;

import com.fiap.pedidos.entities.*;
import com.fiap.pedidos.exceptions.entities.CpfExistenteException;
import com.fiap.pedidos.interfaces.usecases.IClienteUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoProdutoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IPedidoUseCasePort;
import com.fiap.pedidos.interfaces.usecases.IProdutoUseCasePort;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import com.fiap.pedidos.utils.enums.TipoAtualizacao;
import com.fiap.pedidos.utils.enums.TipoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class PedidosApplication {
	@Autowired
	private IProdutoUseCasePort produtoUseCasePort;

	@Autowired
	private IClienteUseCasePort clienteUseCasePort;

	@Autowired
	private IPedidoUseCasePort pedidoUseCasePort;

	@Autowired
	private IPedidoProdutoUseCasePort pedidoProdutoUseCasePort;

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		this.mockProduto();
		this.mockCliente();
		this.mockPedido();
		this.mockCheckout();
	}

	private void mockProduto() {
		for(var i = 0; i<= 5; i++) {
			var quant = i + 1;
			this.produtoUseCasePort.criarProduto(
					Produto.builder().nomeProduto(new NomeProduto(TipoProduto.ACOMPANHAMENTO.name() + quant))
							.descricaoProduto(new DescricaoProduto("Descricao produto: " + quant))
							.tipoProduto(TipoProduto.ACOMPANHAMENTO)
							.valorProduto(new ValorProduto(new BigDecimal(5.0 + quant))).build()
			);
			this.produtoUseCasePort.criarProduto(
					Produto.builder().nomeProduto(new NomeProduto(TipoProduto.BEBIDA.name() + quant))
							.descricaoProduto(new DescricaoProduto("Descricao produto: " + quant))
							.tipoProduto(TipoProduto.BEBIDA)
							.valorProduto(new ValorProduto(new BigDecimal(5.0 + quant))).build()
			);
			this.produtoUseCasePort.criarProduto(
					Produto.builder().nomeProduto(new NomeProduto(TipoProduto.LANCHE.name() + quant))
							.descricaoProduto(new DescricaoProduto("Descricao produto: " + quant))
							.tipoProduto(TipoProduto.LANCHE)
							.valorProduto(new ValorProduto(new BigDecimal(5.0 + quant))).build()
			);
			this.produtoUseCasePort.criarProduto(
					Produto.builder().nomeProduto(new NomeProduto(TipoProduto.SOBREMESA.name() + quant))
							.descricaoProduto(new DescricaoProduto("Descricao produto: " + quant))
							.tipoProduto(TipoProduto.SOBREMESA)
							.valorProduto(new ValorProduto(new BigDecimal(5.0 + quant))).build()
			);
		}
	}

	private void mockCliente(){
		try {
			Cliente cliente = this.clienteUseCasePort.cadastrar(
					Cliente.builder()
							.nome(new Nome("Murilo Benjamin Gabriel das Neves"))
							.email(new Email("murilo-dasneves84@ppe.ufrj.br"))
							.cpf(new Cpf("420.390.450-15"))
							.build());
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.cadastrar(
					Cliente.builder()
							.nome(new Nome("Eliane Catarina Milena Ribeiro"))
							.email(new Email("eliane_catarina_ribeiro@esctech.com.br"))
							.cpf(new Cpf("974.971.471-70"))
							.build());
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.cadastrar(
					Cliente.builder()
							.nome(new Nome("Carlos Eduardo Lucas Paulo Assunção"))
							.email(new Email("carloseduardoassuncao@ambarnet.com.br"))
							.cpf(new Cpf("920.518.758-55"))
							.build());
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.cadastrar(
					Cliente.builder()
							.nome(new Nome("Renato Davi Marcelo Rezende"))
							.email(new Email("renato-rezende99@dddrin.com.br"))
							.cpf(new Cpf("290.358.716-77"))
							.build());
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.identificarPorCpf(new Cpf("161.807.409-17"));
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.identificarPorCpf(new Cpf("624.151.911-59"));
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.identificarPorCpf(new Cpf("311.265.144-89"));
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			Cliente cliente = this.clienteUseCasePort.identificarPorCpf(new Cpf("936.654.027-94"));
			System.out.println(cliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			UUID idCliente = this.clienteUseCasePort.gerarId();
			System.out.println(idCliente);
		} catch (CpfExistenteException e) {
			System.out.println("CPF já cadastrado");
		}

		try {
			List<Cliente> clientes = this.clienteUseCasePort.bucarTodos();
			System.out.println(clientes);
		} catch (CpfExistenteException e) {
			System.err.println("Problemas na listagem de todos clientes");
		}
	}


	private void mockPedido() {
		List<Produto> lanches = produtoUseCasePort.listarProdutosPorTipoProduto(TipoProduto.LANCHE);
		List<Produto> bebidas = produtoUseCasePort.listarProdutosPorTipoProduto(TipoProduto.BEBIDA);
		List<Produto> sobremesas = produtoUseCasePort.listarProdutosPorTipoProduto(TipoProduto.SOBREMESA);
		List<Produto> acompanhamentos = produtoUseCasePort.listarProdutosPorTipoProduto(TipoProduto.ACOMPANHAMENTO);
		List<Cliente> clientes = clienteUseCasePort.bucarTodos();

		for(int i = 0; i<6; i++) {
			try {
				Cliente cliente = clientes.get(i);
				Pedido pedido = pedidoUseCasePort.iniciarPedido(
						Pedido.builder()
								.cliente(cliente)
								.statusPedido(StatusPedido.A)
								.statusPagamento(StatusPagamento.PENDENTE)
								.dataInclusao(new Date())
								.build()
				);
				System.out.println("Pedido: "+pedido.getIdPedido()+" cadadastrado para o cliente: "+cliente.getId());
				if (i == 0) {
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(lanches.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(bebidas.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(sobremesas.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(acompanhamentos.get(i).getIdProduto())
									.build()
					);
				}
				if (i == 1) {
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(lanches.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(lanches.get(i+1).getIdProduto())
									.build()
					);
				}
				if (i == 2) {
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(sobremesas.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(acompanhamentos.get(i).getIdProduto())
									.build()
					);
				}
				if (i == 3) {
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(sobremesas.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(lanches.get(i).getIdProduto())
									.build()
					);
				}
				if (i > 3) {
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(bebidas.get(i).getIdProduto())
									.build()
					);
					pedidoProdutoUseCasePort.adicionarItemNoPedido(
							PedidoProduto.builder()
									.pedidoId(pedido.getIdPedido())
									.produtoId(bebidas.get(i-1).getIdProduto())
									.build()
					);
				}
				System.out.println("Pedido "+i+" inseridos com sucesso");
			} catch (CpfExistenteException e) {
				System.out.println("Problemas ao inserir pedido");
			}
		}
	}

	private void mockCheckout() {

		List<Pedido> pedidos = pedidoUseCasePort.buscarTodos(0, 100);
		for (int i = 0; i<4; i++) {
			try {
				Pedido pedido = pedidos.get(i);
				if (i < 2) {
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.C, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.P, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.F, null, StatusPedido.E);
				}
				if (i == 2) {
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.C, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.P, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.F, null, StatusPedido.R);

				}
				if (i > 2) {
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.C, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.P, null, null);
					pedidoUseCasePort.atualizarPedido(pedido.getIdPedido(), TipoAtualizacao.F, null, StatusPedido.F);
				}
			} catch (Exception e) {
				System.out.println("Problemas ao atualizar status de produtos");
			}
		}
	}
}
