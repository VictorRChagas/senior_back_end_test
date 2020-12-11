package br.com.senior.techicaltest.vendas.pedido.api;

import br.com.senior.techicaltest.vendas.framework.CrudRestController;
import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.pedido.PedidoConverter;
import br.com.senior.techicaltest.vendas.pedido.PedidoService;
import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import br.com.senior.techicaltest.vendas.pedido.model.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController extends CrudRestController<Pedido, UUID, PedidoPersistDto> {

    private final Logger LOGGER = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoConverter pedidoConverter;

    public PedidoController(PedidoService pedidoService, PedidoModelAssembler pedidoModelAssembler,
                            PedidoConverter pedidoConverter) {
        this.pedidoService = pedidoService;
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoConverter = pedidoConverter;
    }

    @Override
    public CrudService<Pedido, UUID> getService() {
        return pedidoService;
    }

    @Override
    public RepresentationModelAssembler<Pedido, EntityModel<Pedido>> getRepresentationModelAssembler() {
        return pedidoModelAssembler;
    }

    @Override
    public ResponseEntity<EntityModel<Pedido>> save(@Valid @RequestBody PedidoPersistDto dto) {
        var pedido = pedidoConverter.mapToPedido(dto);
        var entityModel = pedidoModelAssembler.toModel(pedidoService.save(pedido));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("approve/{id}")
    public ResponseEntity<Boolean> approvePedido(@PathVariable("id") String pedidoId) {
        LOGGER.debug("Approving pedido id: {}", pedidoId);
        return ResponseEntity.ok(pedidoService.approvePedido(pedidoId));
    }
}
