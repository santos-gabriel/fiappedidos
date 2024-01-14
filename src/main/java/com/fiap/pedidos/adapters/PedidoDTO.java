package com.fiap.pedidos.adapters;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PedidoDTO {
    private UUID idPedido;
    private UUID idCliente;
    private List<ProdutoDTO> produtos;
    private StatusPedido statusPedido;
    private BigDecimal valorPedido;
    private Date dataInclusao;
    private Date dataAtualizacao;

    public static PedidoDTO from(Pedido pedido) {
        List<ProdutoDTO> pedidoProdutoDTO = null;
        if (Objects.nonNull(pedido.getProdutos())) {
            pedidoProdutoDTO = pedido.getProdutos().stream()
                    .map(ProdutoDTO::from)
                    .collect(Collectors.toList());
        }
        return PedidoDTO.builder()
                .idPedido(pedido.getIdPedido())
                .idCliente(pedido.getCliente().getId())
                .produtos(pedidoProdutoDTO)
                .statusPedido(pedido.getStatusPedido())
                .valorPedido(pedido.getValorPedido())
                .dataInclusao(pedido.getDataInclusao())
                .dataAtualizacao(pedido.getDataAtualizacao())
                .build();
    }
}
