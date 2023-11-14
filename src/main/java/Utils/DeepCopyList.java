package Utils;

import java.util.ArrayList;
import java.util.List;

import Models.Country;

public class DeepCopyList {

	public static List<Country> deepCopy(List<Country> p_countryList) {
		List<Country> l_newCountryList = new ArrayList<>();
		for (Country l_country : p_countryList) {
			Country l_newCountry = new Country(l_country.getCountryId(), l_country.getContinentId());
			l_newCountry.setArmies(l_country.getArmies());
			l_newCountry.setOwner(l_country.getOwner());
			l_newCountry.setNeighborCountries(l_country.getNeighborCountries());
			l_newCountryList.add(l_newCountry);
		}
		return l_newCountryList;
	}
}
