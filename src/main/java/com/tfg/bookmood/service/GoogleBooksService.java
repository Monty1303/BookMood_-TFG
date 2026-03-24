package com.tfg.bookmood.service;

import com.tfg.bookmood.dto.GoogleBookDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GoogleBooksService(){
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<GoogleBookDto> buscarLibrosPorTitulo (String titulo){
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + titulo.replace(" ","+");
        String response = restTemplate.getForObject(url, String.class);
        List<GoogleBookDto> resultados = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.get("items");

            if (items != null && items.isArray()){
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
        }catch (Exception e){
            throw new RuntimeException("Error al procesar la petición de Google Books", e);
        }
        return resultados;
    }


}
