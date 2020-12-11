package br.com.senior.techicaltest.vendas.framework;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Collectors;

@Component
public abstract class CrudRestController<T, ID, X> {

    private final Logger LOGGER = LoggerFactory.getLogger("crudrest");

    public abstract CrudService<T, ID> getService();

    public abstract RepresentationModelAssembler<T, EntityModel<T>> getRepresentationModelAssembler();

    @Autowired
    protected ModelMapper modelMapper;

    private final Class persistentClass;

    public CrudRestController() {
        this.persistentClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @PostMapping
    public ResponseEntity<EntityModel<T>> save(@NonNull @Valid @RequestBody X dto) {
        LOGGER.debug("Saving new entity");
        var object = (T) modelMapper.map(dto, persistentClass);
        var entityModel = getRepresentationModelAssembler().toModel(getService().save(object));

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<T>> updateById(@PathVariable("id") ID id, @RequestBody X dto) {
        LOGGER.debug("Updating entity");
        var object = getService().findById(id);
        postFindOneOnUpdate(object, dto);
        modelMapper.map(dto, object);
        var entityModel = getRepresentationModelAssembler().toModel(getService().save(object));

        return ResponseEntity.ok(entityModel);
    }

    protected void postFindOneOnUpdate(T object, X dto) {
    }

    private void postFindOneOnUpdate(T object) {
    }

    @GetMapping
    public CollectionModel<EntityModel<T>> findAll(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        LOGGER.debug("Fetching all {}");
        var pageable = PageRequest.of(page, size);

        var objectList = getService().findAll(pageable)
                .stream().map(getRepresentationModelAssembler()::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(objectList);
    }

    @GetMapping("{id}")
    public EntityModel<T> findById(@NonNull @PathVariable("id") ID id) {
        LOGGER.debug("Searching by id {}", id);
        return getRepresentationModelAssembler().toModel(getService().findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@NonNull @PathVariable ID id) {
        return getService().deleteById(id);
    }
}
