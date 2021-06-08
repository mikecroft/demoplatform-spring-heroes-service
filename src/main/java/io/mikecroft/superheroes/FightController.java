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
public class FightController {
    
    private final HeroRepository heroRepository;

    @Autowired
    public FightController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }
    
    
    @GetMapping("/fight/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hero Id:" + id));
        model.addAttribute("hero", hero);
        
        return "fight-hero";
    }
    
    // @PostMapping("/update/{id}")
    // public String updateHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         hero.setId(id);
    //         return "fight-hero";
    //     }
    //     heroRepository.save(hero);

    //     return "redirect:/index";
    // }

    @PostMapping("/punch/{id}")
    public String punchHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        hero.setHp(hero.getHp() - 7);
        heroRepository.save(hero);

        return "fight-hero";
    }

    @PostMapping("/kick/{id}")
    public String kickHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        hero.setHp(hero.getHp() - 13);
        heroRepository.save(hero);

        return "fight-hero";
    }

    @PostMapping("/curse/{id}")
    public String curseHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        hero.setHp(hero.getHp() - 11);
        heroRepository.save(hero);
        return "fight-hero";
    }

    
}