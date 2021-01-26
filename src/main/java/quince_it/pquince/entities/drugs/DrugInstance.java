package quince_it.pquince.entities.drugs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class DrugInstance extends Drug {
	
	@Column(name = "drugInstanceName", nullable = false)
	private String drugInstanceName;
	
	@ManyToOne
	private Manufacturer manufacturer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drugFormat", nullable = false)
	private FormatDrug drugFormat;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drugKind", nullable = false)
	private DrugKind drugKind;
	
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	@Column(name = "sideEffects", nullable = false)
	private String sideEffects;
	
	@Column(name = "recommendedAmount", nullable = false)
	private String recommendedAmount;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "drug_replacement",
            joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "replacement_drug_id", referencedColumnName = "id"))
	 private List<DrugInstance> replacingDrugs;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "drug_allergen",
            joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "id"))
    private List<Allergen> allergens;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "drug_ingredients",
            joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private List<Ingredient> ingredients;
	
	@Column(name = "loyalityPoints")
	private int loyalityPoints;
	
	@Column(name = "onReciept")
	private boolean onReciept;

	public DrugInstance() {
		super();
	}

	public DrugInstance(String name, String code, String drugInstanceName, Manufacturer manufacturer, FormatDrug drugFormat, double quantity,
			String sideEffects, String recommendedAmount, List<DrugInstance> replacingDrugs, List<Allergen> allergens,
			List<Ingredient> ingredients, int loyalityPoints, boolean onReciept, DrugKind drugKind) {
		super(UUID.randomUUID(),name, code);
		this.drugInstanceName = drugInstanceName;
		this.manufacturer = manufacturer;
		this.drugFormat = drugFormat;
		this.quantity = quantity;
		this.sideEffects = sideEffects;
		this.recommendedAmount = recommendedAmount;
		this.replacingDrugs = replacingDrugs;
		this.allergens = allergens;
		this.ingredients = ingredients;
		this.loyalityPoints = loyalityPoints;
		this.onReciept = onReciept;
		this.drugKind = drugKind;
	}

	public DrugInstance(UUID id, String name, String code, String drugInstanceName, Manufacturer manufacturer, FormatDrug drugFormat, double quiantity,
			String sideEffects, String recommendedAmount, List<DrugInstance> replacingDrugs, List<Allergen> allergens,
			List<Ingredient> ingredients, int loyalityPoints, boolean onReciept, DrugKind drugKind) {
		super(id, name, code);
		this.drugInstanceName = drugInstanceName;
		this.manufacturer = manufacturer;
		this.drugFormat = drugFormat;
		this.quantity = quiantity;
		this.sideEffects = sideEffects;
		this.recommendedAmount = recommendedAmount;
		this.replacingDrugs = replacingDrugs;
		this.allergens = allergens;
		this.ingredients = ingredients;
		this.loyalityPoints = loyalityPoints;
		this.onReciept = onReciept;
		this.drugKind = drugKind;
	}

	public String getDrugInstanceName() {
		return drugInstanceName;
	}

	public void setDrugInstanceName(String drugInstanceName) {
		this.drugInstanceName = drugInstanceName;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}

	public String getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(String recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	public int getLoyalityPoints() {
		return loyalityPoints;
	}

	public void setLoyalityPoints(int loyalityPoints) {
		this.loyalityPoints = loyalityPoints;
	}

	public boolean isOnReciept() {
		return onReciept;
	}

	public void setOnReciept(boolean onReciept) {
		this.onReciept = onReciept;
	}

	public FormatDrug getDrugFormat() {
		return drugFormat;
	}

	public List<DrugInstance> getReplacingDrugs() {
		return replacingDrugs;
	}

	public List<Allergen> getAllergens() {
		return allergens;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void addIngredient(Ingredient ingredient) {
		if(this.ingredients == null)
			this.ingredients = new ArrayList<Ingredient>();
		
		this.ingredients.add(ingredient);
	}
	
	public void addAllergen(Allergen allergen) {
		if(this.allergens == null)
			this.allergens = new ArrayList<Allergen>();
		
		this.allergens.add(allergen);
	}
	
	public void addReplaceDrug(DrugInstance drug) {
		if(this.replacingDrugs == null)
			this.replacingDrugs = new ArrayList<DrugInstance>();
		
		this.replacingDrugs.add(drug);
	}

	public DrugKind getDrugKind() {
		return drugKind;
	}

	public void setDrugKind(DrugKind drugKind) {
		this.drugKind = drugKind;
	}
	
}
