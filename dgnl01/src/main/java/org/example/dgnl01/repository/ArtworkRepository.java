package org.example.dgnl01.repository;

import org.example.dgnl01.entity.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    Page<Artwork> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(
            String title, String artist, Pageable pageable);
}
