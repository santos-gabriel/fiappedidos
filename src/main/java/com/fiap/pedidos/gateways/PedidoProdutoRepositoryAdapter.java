package com.fiap.pedidos.gateways;

import com.fiap.pedidos.entities.Pedido;
import com.fiap.pedidos.entities.PedidoProduto;
import com.fiap.pedidos.exceptions.entities.PedidoNaoEncontradoException;
import com.fiap.pedidos.exceptions.entities.PedidoOperacaoNaoSuportadaException;
import com.fiap.pedidos.exceptions.entities.PedidoProdutoNaoEncontradoException;
import com.fiap.pedidos.gateways.entities.PedidoEntity;
import com.fiap.pedidos.gateways.entities.PedidoProdutoEntity;
import com.fiap.pedidos.gateways.entities.ProdutoEntity;
import com.fiap.pedidos.interfaces.gateways.IPedidoProdutoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.PedidoProdutoRepository;
import com.fiap.pedidos.interfaces.repositories.PedidoRepository;
import com.fiap.pedidos.interfaces.repositories.ProdutoRepository;
import com.fiap.pedidos.utils.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoProdutoRepositoryAdapter implements IPedidoProdutoRepositoryPort {

    private final PedidoProdutoRepository pedidoProdutoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoProduto> buscarTodos() {
        return this.pedidoProdutoRepository.findAll().stream()
                .map(PedidoProdutoEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoProduto> buscarPorId(UUID id) {
        return this.pedidoProdutoRepository.findById(id)
                .map(PedidoProdutoEntity::to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoProduto> buscarPorPedido(Pedido pedido) {
        PedidoEntity pedidoEntity = this.pedidoRepository.findById(pedido.getIdPedido())
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado, id: " + pedido.getIdPedido()));

        return this.pedidoProdutoRepository.findByPedido(pedido)
                .stream()
                .map(PedidoProdutoEntity::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoProduto adicionarPedidoProduto(PedidoProduto pedidoProduto) {
        PedidoEntity existPedioEntity = pedidoRepository.findById(pedidoProduto.getPedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado, id: " + pedidoProduto.getPedidoId()));
        ProdutoEntity existProdutoEntity = produtoRepository.findById(pedidoProduto.getProdutoId())
                .orElseThrow(() -> new PedidoProdutoNaoEncontradoException("Produto não encontrado, id: " + pedidoProduto.getProdutoId()));

        PedidoProdutoEntity pedidoProdutoEntity = PedidoProdutoEntity.builder()
                .id(pedidoProduto.getId())
                .pedido(existPedioEntity)
                .produto(existProdutoEntity)
                .valorProduto(pedidoProduto.getValorProduto())
                .observacaoProduto(pedidoProduto.getObservacaoProduto())
                .build();

        existPedioEntity.setDataAtualizacao(new Date());
        pedidoRepository.save(existPedioEntity);

        return PedidoProdutoEntity.to(this.pedidoProdutoRepository.save(pedidoProdutoEntity));
    }


    @Override
    @Transactional
    public PedidoProduto editarPedidoProduto(PedidoProduto pedidoProduto) {
        PedidoProdutoEntity existingPedidoProdutoEntity = this.pedidoProdutoRepository.findById(pedidoProduto.getId())
                .orElseThrow(() -> new PedidoProdutoNaoEncontradoException("PedidoProduto não encontrado, id: " + pedidoProduto.getId()));
        PedidoEntity existPedioEntity = pedidoRepository.findById(pedidoProduto.getPedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado, id: " + pedidoProduto.getPedidoId()));
        ProdutoEntity existProdutoEntity = produtoRepository.findById(existingPedidoProdutoEntity.getProduto().getIdProduto())
                .orElseThrow(() -> new PedidoProdutoNaoEncontradoException("Produto não encontrado, id: " + pedidoProduto.getProdutoId()));

        existingPedidoProdutoEntity.setPedido(existPedioEntity);
        existingPedidoProdutoEntity.setProduto(existProdutoEntity);
        existingPedidoProdutoEntity.setValorProduto(pedidoProduto.getValorProduto());
        existingPedidoProdutoEntity.setObservacaoProduto(pedidoProduto.getObservacaoProduto());

        PedidoProdutoEntity updatedPedidoProdutoEntity = this.pedidoProdutoRepository.save(existingPedidoProdutoEntity);

        existPedioEntity.setDataAtualizacao(new Date());
        pedidoRepository.save(existPedioEntity);

        return PedidoProdutoEntity.to(updatedPedidoProdutoEntity);
    }

    @Override
    @Transactional
    public void excluirPedidoProduto(UUID id) {
        Optional<PedidoProdutoEntity> pedidoProdutoOptional = pedidoProdutoRepository.findById(id);
        if (pedidoProdutoOptional.isPresent()) {
            PedidoProdutoEntity pedidoProdutoEntity = pedidoProdutoOptional.get();
            Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(pedidoProdutoEntity.getPedido().getIdPedido());
            if (pedidoOptional.isPresent()) {
                PedidoEntity pedidoEntity = pedidoOptional.get();
                if (pedidoEntity.getStatusPedido() == StatusPedido.A) {
                    pedidoProdutoRepository.deleteById(id);
                    pedidoEntity.setDataAtualizacao(new Date());
                    pedidoRepository.save(pedidoEntity);
                } else {
                    throw new PedidoOperacaoNaoSuportadaException("Pedido status is not A, id: " + pedidoEntity.getIdPedido());
                }
                }
            }
        }
    }
