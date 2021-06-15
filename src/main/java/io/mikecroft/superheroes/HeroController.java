package io.mikecroft.superheroes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

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
public class HeroController {
    
    private final HeroRepository heroRepository;

    private Integer myHP;
    private Counter recoveryCounter;
    private AtomicInteger hpGauge;
    private MeterRegistry meterRegistry;
    private String hostname;

    @Autowired
    public HeroController(HeroRepository heroRepository,MeterRegistry meterRegistry) {
        this.heroRepository = heroRepository;
        this.meterRegistry = meterRegistry;
        this.recoveryCounter = this.meterRegistry.counter("fight.recovery");
        this.hpGauge = this.meterRegistry.gauge("fight.challenger.hp", new AtomicInteger(100));
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping({"/index", "/"})
    public String showHeroList(Model model, HttpServletRequest request, HttpSession session) {
        model.addAttribute("heroes", heroRepository.findAll());

        myHP = (Integer) session.getAttribute("MY_HP");
		if (myHP == null) {
			myHP = 100;
		}
		model.addAttribute("myHP", myHP);
        request.getSession().setAttribute("MY_HP", myHP);
        request.getSession().setAttribute("HOSTNAME", hostname);

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

    @GetMapping("/recover")
    public String recover(Model model, HttpServletRequest request) {
        if (myHP < 96){
            myHP += 5;
        } else {
            myHP = 100;
        }
        hpGauge.set(myHP);
        request.getSession().setAttribute("MY_HP", myHP);
        recoveryCounter.increment();
        return "redirect:/index";
    }
}