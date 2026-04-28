package org.example.dgnl01.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.example.dgnl01.entity.Artwork;
import org.example.dgnl01.service.ArtworkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostConstruct
    public void init() {
        artworkService.seedData();
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) String keyword,
                       Model model) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Artwork> artworkPage = artworkService.search(keyword, pageable);

        model.addAttribute("artworkPage", artworkPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("formAction", "/artworks/save");
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("artwork") Artwork artwork,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/artworks/save");
            return "form";
        }
        artworkService.save(artwork);
        return "redirect:/artworks";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.findById(id);
        if (artwork == null) {
            return "redirect:/artworks";
        }
        model.addAttribute("artwork", artwork);
        model.addAttribute("formAction", "/artworks/update/" + id);
        return "form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("artwork") Artwork artwork,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/artworks/update/" + id);
            return "form";
        }
        artwork.setId(id);
        artworkService.save(artwork);
        return "redirect:/artworks";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        artworkService.deleteById(id);
        return "redirect:/artworks";
    }
}
