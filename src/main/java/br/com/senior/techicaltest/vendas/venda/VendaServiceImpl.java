package br.com.senior.techicaltest.vendas.venda;

import br.com.senior.techicaltest.vendas.framework.CrudServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class VendaServiceImpl extends CrudServiceImpl<Venda, String> implements VendaService {

    private final VendaRepository repository;

    public VendaServiceImpl(VendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Venda, String> getRepository() {
        return repository;
    }
}
