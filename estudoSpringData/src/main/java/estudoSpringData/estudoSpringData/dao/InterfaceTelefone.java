package estudoSpringData.estudoSpringData.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import estudoSpringData.estudoSpringData.model.Telefone;

@Repository
public interface InterfaceTelefone extends CrudRepository<Telefone, Long>{

}
