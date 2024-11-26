package com.aluracursos.screenmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;


public interface SerieRepository extends JpaRepository<Serie, Long>{

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    // List<Serie> findByTotaldeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totaldeTemporadas, Double evaluacion);

    // ************Querys Nativas fijas*************

    /*@Query(value = "SELECT * FROM series WHERE series.totalde_temporadas >= 5 AND series.evaluacion <= 9", nativeQuery = true)
    List<Serie> buscarPorQueryNativas(); */

    // ************Querys Nativas con variante*************
    @Query("SELECT s FROM Serie s WHERE s.totaldeTemporadas >= :totaldeTemporadas AND s.evaluacion <= :evaluacion")
    List<Serie> buscarPorQueryNativas(int totaldeTemporadas, Double evaluacion);
}
