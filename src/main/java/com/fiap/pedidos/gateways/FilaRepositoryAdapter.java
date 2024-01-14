package com.fiap.pedidos.gateways;

import com.fiap.pedidos.interfaces.gateways.IFilaRepositoryPort;
import com.fiap.pedidos.interfaces.gateways.IPagamentoRepositoryPort;
import com.fiap.pedidos.interfaces.repositories.FilaRepository;
import com.fiap.pedidos.interfaces.repositories.PagamentoRepository;
import com.fiap.pedidos.utils.enums.StatusPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilaRepositoryAdapter implements IFilaRepositoryPort {

    private final FilaRepository filaRepository;

    @Override
    public void inserePedidoNaFila(UUID idPedido, UUID idCliente) {
        this.filaRepository.inserePedidoNaFila(idPedido, idCliente);
    }
}
