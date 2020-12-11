package br.com.senior.techicaltest.vendas.pedido;

import br.com.senior.techicaltest.vendas.framework.CrudServiceImpl;
import br.com.senior.techicaltest.vendas.pedido.event.PedidoPreSaveEvent;
import br.com.senior.techicaltest.vendas.pedido.validations.PedidoValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoServiceImpl extends CrudServiceImpl<Pedido, UUID> implements PedidoService {

    private final PedidoRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private List<PedidoValidations> pedidoValidationsList;

    public PedidoServiceImpl(PedidoRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public JpaRepository<Pedido, UUID> getRepository() {
        return repository;
    }

    @Override
    protected void preSave(Pedido entity) {
        pedidoValidationsList.forEach(pedidoValidations -> pedidoValidations.validate(entity));
        applicationEventPublisher.publishEvent(new PedidoPreSaveEvent(this, entity));
    }

    @Override
    public Boolean approvePedido(String pedidoId) {
        return repository.approveOrder(pedidoId) == 1;
    }
}
