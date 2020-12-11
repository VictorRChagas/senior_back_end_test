package br.com.senior.techicaltest.vendas.venda.api;

import br.com.senior.techicaltest.vendas.framework.CrudRestController;
import br.com.senior.techicaltest.vendas.framework.CrudService;
import br.com.senior.techicaltest.vendas.venda.Venda;
import br.com.senior.techicaltest.vendas.venda.VendaConverter;
import br.com.senior.techicaltest.vendas.venda.VendaService;
import br.com.senior.techicaltest.vendas.venda.dto.VendaPersistDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/venda")
public class VendaController extends CrudRestController<Venda, String, VendaPersistDto> {

    private final VendaService vendaService;
    private final VendaModelAssembler vendaModelAssembler;
    private final VendaConverter vendaConverter;

    public VendaController(VendaService vendaService, VendaModelAssembler vendaModelAssembler, VendaConverter vendaConverter) {
        this.vendaService = vendaService;
        this.vendaModelAssembler = vendaModelAssembler;
        this.vendaConverter = vendaConverter;
    }

    @Override
    public CrudService<Venda, String> getService() {
        return vendaService;
    }

    @Override
    public RepresentationModelAssembler<Venda, EntityModel<Venda>> getRepresentationModelAssembler() {
        return vendaModelAssembler;
    }

    @Override
    public ResponseEntity<EntityModel<Venda>> save(@Valid @RequestBody VendaPersistDto dto) {
        var venda = vendaConverter.mapToVenda(dto);
        var entityModel = vendaModelAssembler.toModel(vendaService.save(venda));
        return ResponseEntity.ok(entityModel);
    }
}
