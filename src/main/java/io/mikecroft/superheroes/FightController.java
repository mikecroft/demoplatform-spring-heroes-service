package io.mikecroft.superheroes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Controller
public class FightController {
    
    private final HeroRepository heroRepository;

    private Integer myHP;
    
    private MeterRegistry meterRegistry;

    private Counter punchCounter;
    private Counter kickCounter;
    private Counter curseCounter;

    @Autowired
    public FightController(HeroRepository heroRepository, MeterRegistry meterRegistry) {
        this.heroRepository = heroRepository;
        this.meterRegistry = meterRegistry;
        punchCounter = this.meterRegistry.counter("fight.punches");
        kickCounter = this.meterRegistry.counter("fight.kicks");
        curseCounter = this.meterRegistry.counter("fight.curses");
    }

    @GetMapping("/fight/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, HttpServletRequest request, HttpSession session) {
        Hero hero = heroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hero Id:" + id));
        model.addAttribute("hero", hero);

        myHP = (Integer) session.getAttribute("MY_HP");
		if (myHP == null) {
			myHP = 100;
		}

		model.addAttribute("myHP", myHP);
        request.getSession().setAttribute("MY_HP", myHP);
        
        return "fight-hero";
    }

    @PostMapping("/punch/{id}")
    public String punchHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        int heroHP = hero.getHp() - 7;
        hero.setHp(heroHP < 0 ? 0 : heroHP);
        heroRepository.save(hero);

        myHP -= 4;
		request.getSession().setAttribute("MY_HP", myHP < 0 ? 0 : myHP);

        punchCounter.increment();
        return "fight-hero";
    }

    @PostMapping("/kick/{id}")
    public String kickHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        int heroHP = hero.getHp() - 13;
        hero.setHp(heroHP < 0 ? 0 : heroHP);
        heroRepository.save(hero);

        myHP -= 6;
		request.getSession().setAttribute("MY_HP", myHP < 0 ? 0 : myHP);

        kickCounter.increment();
        return "fight-hero";
    }

    @PostMapping("/curse/{id}")
    public String curseHero(@PathVariable("id") long id, @Valid Hero hero, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            hero.setId(id);
            return "fight-hero";
        }
        int heroHP = hero.getHp() - 11;
        hero.setHp(heroHP < 0 ? 0 : heroHP);
        heroRepository.save(hero);

        myHP -= 5;
		request.getSession().setAttribute("MY_HP", myHP < 0 ? 0 : myHP);

        curseCounter.increment();
        return "fight-hero";
    }

    
}