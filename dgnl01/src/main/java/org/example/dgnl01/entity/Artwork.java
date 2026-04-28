package org.example.dgnl01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên tác phẩm không được để trống")
    @Size(min = 5, max = 150, message = "Tên tác phẩm phải từ 5 đến 150 ký tự")
    private String title;

    @NotBlank(message = "Tên họa sĩ không được để trống")
    @Pattern(regexp = "\\S+(\\s\\S+)*", message = "Tên họa sĩ không được để khoảng trắng đầu/cuối")
    private String artist;

    @NotNull(message = "Ngày sáng tác không được để trống")
    @PastOrPresent(message = "Ngày sáng tác không được là ngày trong tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Artwork() {
    }

    public Artwork(String title, String artist, LocalDate date) {
        this.title = title;
        this.artist = artist;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
