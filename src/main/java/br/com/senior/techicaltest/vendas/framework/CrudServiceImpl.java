package br.com.senior.techicaltest.vendas.framework;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import javax.persistence.NoResultException;
import java.util.List;

public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    public abstract JpaRepository<T, ID> getRepository();

    @Override
    public Page<T> findAll(PageRequest pageable) {
        var all = getRepository().findAll(pageable);
        postFindAll(all);
        return all;
    }

    protected void postFindAll(Page<T> all) {
    }

    @Override
    public T findById(ID id) {
        var object = getRepository().findById((ID) id)
                .orElseThrow(NoResultException::new);
        postFindOne(object);
        return object;

    }

    protected void postFindOne(T object) {
    }

    @Override
    public T save(T entity) {
        preSave(entity);
        T savedEntity = getRepository().save(entity);
        postSave(savedEntity);
        return savedEntity;
    }

    @Override
    public List<T> saveAll(List<T> entityList) {
        return getRepository().saveAll(entityList);
    }

    protected void postSave(T savedEntity) {

    }

    protected void preSave(T entity) {

    }

    @Override
    public ResponseEntity<?> deleteById(ID id) {
        if (getRepository().existsById(id)) {
            getRepository().deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new NoResultException();
    }
}
