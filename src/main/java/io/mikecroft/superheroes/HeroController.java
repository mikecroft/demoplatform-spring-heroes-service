package io.mikecroft.superheroes;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HeroController {
    
    private final HeroRepository heroRepository;

    @Autowired
    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }
    
    @GetMapping("/index")
    public String showHeroList(Model model) {
        model.addAttribute("heroes", heroRepository.findAll());
        return "index";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(Hero hero) {
        return "add-hero";
    }
    
    @PostMapping("/addhero")
    public String addHero(@Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-hero";
        }
        
        heroRepository.save(hero);
        return "redirect:/index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hero Id:" + id));
        model.addAttribute("hero", hero);
        
        return "update-hero";
    }
    
    @PostMapping("/update/{id}")
    public String updateHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "update-hero";
        }
        
        heroRepository.save(hero);

        return "redirect:/index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteHero(@PathVariable("id") long id, Model model) {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hero Id:" + id));
        heroRepository.delete(hero);
        
        return "redirect:/index";
    }
}