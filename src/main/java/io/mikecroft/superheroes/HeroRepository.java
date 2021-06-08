package io.mikecroft.superheroes;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "heroes", path = "heroes")
public interface HeroRepository extends PagingAndSortingRepository<Hero, Long> {

	List<Hero> findByName(@Param("name") String name);
    List<Hero> findByPower(@Param("power") String power);
    List<Hero> findByWeakness(@Param("weakness") String weakness);

}