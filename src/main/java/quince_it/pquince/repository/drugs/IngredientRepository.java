package quince_it.pquince.repository.drugs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import quince_it.pquince.entities.drugs.Ingredient;
import quince_it.pquince.entities.users.User;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>{

	Ingredient findByName ( String name );

}
