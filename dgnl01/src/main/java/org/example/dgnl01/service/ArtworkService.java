package org.example.dgnl01.service;

import org.example.dgnl01.entity.Artwork;
import org.example.dgnl01.repository.ArtworkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Page<Artwork> findAll(Pageable pageable) {
        return artworkRepository.findAll(pageable);
    }

    public Page<Artwork> search(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return artworkRepository.findAll(pageable);
        }
        return artworkRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(
                keyword, keyword, pageable);
    }

    public Artwork findById(Long id) {
        return artworkRepository.findById(id).orElse(null);
    }

    public void save(Artwork artwork) {
        artworkRepository.save(artwork);
    }

    public void deleteById(Long id) {
        artworkRepository.deleteById(id);
    }

    public void seedData() {
        if (artworkRepository.count() == 0) {
            List<Artwork> list = List.of(
                    new Artwork("Mona Lisa", "Leonardo", LocalDate.of(1503, 1, 1)),
                    new Artwork("The Starry Night", "VanGogh", LocalDate.of(1889, 6, 1)),
                    new Artwork("The Scream", "Munch", LocalDate.of(1893, 1, 1)),
                    new Artwork("Guernica", "Picasso", LocalDate.of(1937, 6, 4)),
                    new Artwork("The Persistence of Memory", "Dali", LocalDate.of(1931, 1, 1)),
                    new Artwork("Girl with a Pearl Earring", "Vermeer", LocalDate.of(1665, 1, 1)),
                    new Artwork("The Birth of Venus", "Botticelli", LocalDate.of(1485, 1, 1)),
                    new Artwork("The Night Watch", "Rembrandt", LocalDate.of(1642, 1, 1)),
                    new Artwork("The Kiss", "Klimt", LocalDate.of(1908, 1, 1)),
                    new Artwork("Water Lilies", "Monet", LocalDate.of(1906, 1, 1)),
                    new Artwork("American Gothic", "Wood", LocalDate.of(1930, 1, 1)),
                    new Artwork("The Last Supper", "Leonardo", LocalDate.of(1498, 1, 1))
            );
            artworkRepository.saveAll(list);
        }
    }
}
