package br.com.ejrocha.api.repository;

import br.com.ejrocha.api.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findByCodigoIsoIgnoreCase(String codigoIso);
    List<Pais> findAllByOrderByNomeAsc();
    List<Pais> findAllByOrderByPopulacaoDesc();
    List<Pais> findAllByOrderByAreaDesc();
}
