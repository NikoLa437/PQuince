package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>{

}
