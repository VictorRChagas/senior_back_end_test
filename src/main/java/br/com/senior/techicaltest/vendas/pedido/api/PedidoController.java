package br.com.senior.techicaltest.vendas.pedido.api;

import br.com.senior.techicaltest.vendas.framework.CrudRestController;
import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.pedido.Pedido;
import br.com.senior.techicaltest.vendas.pedido.PedidoConverter;
import br.com.senior.techicaltest.vendas.pedido.PedidoService;
import br.com.senior.techicaltest.vendas.pedido.dto.PedidoPersistDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/pedido")
public class PedidoController extends CrudRestController<Pedido, String, PedidoPersistDto> {

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
    public CrudService<Pedido, String> getService() {
        return pedidoService;
    }

    @Override
    public RepresentationModelAssembler<Pedido, EntityModel<Pedido>> getRepresentationModelAssembler() {
        return pedidoModelAssembler;
    }

    @Override
    public ResponseEntity<EntityModel<Pedido>> save(@Valid PedidoPersistDto dto) {
        var pedido = pedidoConverter.mapToPedido(dto);
        var entityModel = pedidoModelAssembler.toModel(pedidoService.save(pedido));
        return ResponseEntity.ok(entityModel);
    }
}
