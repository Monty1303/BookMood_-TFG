package com.tfg.bookmood.service;

import com.tfg.bookmood.dto.GoogleBookDto;
import com.tfg.bookmood.model.EstadoAnimo;
import com.tfg.bookmood.model.Libro;
import com.tfg.bookmood.model.LibroEstadoAnimo;
import com.tfg.bookmood.repository.EstadoAnimoRepository;
import com.tfg.bookmood.repository.LibroEstadoAnimoRepository;
import com.tfg.bookmood.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final LibroRepository libroRepository;
    private final EstadoAnimoRepository estadoAnimoRepository;
    private final LibroEstadoAnimoRepository libroEstadoAnimoRepository;
    @Value("${google.books.api.key}")
    private String googleBooksApiKey;

    public GoogleBooksService(
            LibroRepository libroRepository,
            EstadoAnimoRepository estadoAnimoRepository,
            LibroEstadoAnimoRepository libroEstadoAnimoRepository
    ){
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.libroRepository = libroRepository;
        this.estadoAnimoRepository = estadoAnimoRepository;
        this.libroEstadoAnimoRepository = libroEstadoAnimoRepository;
    }

    public List<GoogleBookDto> buscarLibrosPorTitulo (String titulo){
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + titulo.replace(" ","+")
                + "&langRestrict=es"+ "&maxResults=13"+"&orderBy=newest"+"&printType=books"
                +"&key="+ googleBooksApiKey;

        List<GoogleBookDto> resultados = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.get("items");

            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    JsonNode volumeInfo = item.path("volumeInfo");

                    String tituloLibro = volumeInfo.path("title").asText("Sin título");

                    String autor = "Autor desconocido";
                    JsonNode authorsNode = volumeInfo.path("authors");
                    if (authorsNode.isArray() && authorsNode.size() > 0) {
                        autor = authorsNode.get(0).asText();
                    }

                    String descripcion = volumeInfo.path("description").asText("Sin descripción");

                    String portadaUrl = "";
                    JsonNode imageLinksNode = volumeInfo.path("imageLinks");
                    if (imageLinksNode.has("thumbnail")) {
                        portadaUrl = imageLinksNode.get("thumbnail").asText();
                    }

                    resultados.add(new GoogleBookDto(tituloLibro, autor, descripcion, portadaUrl));

                }
            }
        } catch (HttpServerErrorException.ServiceUnavailable e) {

            System.out.println("Google Books no disponible temporalmente");
            return resultados;
        } catch (RestClientException e) {
            System.out.println("Error llamando a Google Books: " + e.getMessage());
            return resultados;
        }catch (Exception e){
            throw new RuntimeException("Error al procesar la petición de Google Books", e);
        }
        return resultados;
    }

    public String importarLibrosPorMood (Long moodId, String tituloBusqueda){
        EstadoAnimo estadoAnimo = estadoAnimoRepository.findById(moodId)
                .orElseThrow(()-> new RuntimeException("Estado de ánimo no encontrado"));

        List <GoogleBookDto> librosGoogle = buscarLibrosPorTitulo(tituloBusqueda);

        int librosNuevos = 0;
        int relacionesNuevas = 0;

        for (GoogleBookDto dto : librosGoogle){
            String portadaSegura = dto.portadaUrl != null
                    ? dto.portadaUrl.replace("http://","https://"):"";

            var libroExistente = libroRepository.findByTituloAndAutor(dto.titulo,dto.autor);

            Libro libroGuardado = libroExistente.orElseGet(()->{
                Libro nuevoLibro = new Libro();
                nuevoLibro.setTitulo(dto.titulo);
                nuevoLibro.setAutor(dto.autor);
                nuevoLibro.setSinopsis(dto.descripcion);
                nuevoLibro.setPortadaUrl(portadaSegura);
                nuevoLibro.setGenero("Sin género");
                return libroRepository.save(nuevoLibro);
            });

            if (libroExistente.isEmpty()){
                librosNuevos++;
            }

            boolean existeRelacion = libroEstadoAnimoRepository
                    .existsByLibroIdLibroAndEstadoAnimoIdEstadoAnimo(
                            libroGuardado.getIdLibro(),
                            estadoAnimo.getIdEstadoAnimo()
                    );

            if (!existeRelacion){
                LibroEstadoAnimo relacion = new LibroEstadoAnimo();
                relacion.setLibro(libroGuardado);
                relacion.setEstadoAnimo(estadoAnimo);
                libroEstadoAnimoRepository.save(relacion);
                relacionesNuevas++;
            }
        }
        return "Importación completada. Libros nuevos: "+librosNuevos + ". Relaciones nuevas: "+relacionesNuevas;
    }


}
