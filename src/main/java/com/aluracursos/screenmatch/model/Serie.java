package com.aluracursos.screenmatch.model;

import java.util.OptionalDouble;

// import com.aluracursos.screenmatch.service.ConsultaChatGtp;

public class Serie {
    private String titulo;
    private int totaldeTemporadas;
    private Double evaluacion;
    private Categoria genero;
    private String actores;
    private String poster;
    private String sinopsis;

    public Serie(DataSerie dataSerie) {
        this.titulo = dataSerie.titulo();
        this.totaldeTemporadas = dataSerie.totaldeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(dataSerie.evaluacion())).orElse(0);
        this.genero = Categoria.fromString(dataSerie.genero().split(",")[0].trim());
        this.actores = dataSerie.actores();
        this.poster = dataSerie.poster();
        // this.sinopsis = ConsultaChatGtp.obtenerTraduccion(dataSerie.sinopsis());
        this.sinopsis = dataSerie.sinopsis();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotaldeTemporadas() {
        return totaldeTemporadas;
    }

    public void setTotaldeTemporadas(int totaldeTemporadas) {
        this.totaldeTemporadas = totaldeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "Titulo= " + titulo + ", Total de Temporadas= " + totaldeTemporadas + ", Evaluación= " + evaluacion
                + ", Genero= " + genero + ", Actores= " + actores + ", Poster= " + poster + ", Sinopsis= " + sinopsis;
    }
}
