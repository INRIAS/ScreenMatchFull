package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DataSerie;
import com.aluracursos.screenmatch.model.DataTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoApi;
import com.aluracursos.screenmatch.service.ConvertirDatos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PrincipalRepository {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f33c21cc";
    private ConvertirDatos conversor = new ConvertirDatos();
    private List<DataSerie> dataSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;

    public PrincipalRepository(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series.
                    2 - Buscar episodios.
                    3 - Mostrar series buscadas.
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostraSeriesBuscadas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DataSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DataSerie datos = conversor.obtenerDatos(json, DataSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        mostraSeriesBuscadas();

        System.out.println("Ingresar serie de los episodios a ver: ");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DataTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotaldeTemporadas(); i++) {
                var json = consumoApi
                        .obtenerDatos(
                                URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DataTemporadas datosTemporada = conversor.obtenerDatos(json, DataTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numeroTemporada(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }

    }

    private void buscarSerieWeb() {
        DataSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        // dataSeries.add(datos);
        // System.out.println(datos);
    }

    private void mostraSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}
