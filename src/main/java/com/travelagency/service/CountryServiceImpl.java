package com.travelagency.service;

import com.travelagency.model.Country;
import com.travelagency.repository.CountryRepository;
import com.travelagency.slugify.TagSlugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService{
    private final CountryRepository countryRepository;

    private final TagSlugify tagSlugify = new TagSlugify();

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country saveCountry(Country country) {
        StringBuilder slug = new StringBuilder(tagSlugify.slugify(country.getName()));
        List<Country> foundCountry = countryRepository.findByTagContains(String.valueOf(slug));
        if(!foundCountry.isEmpty()){
            slug = tagSlugify.slugify(foundCountry, slug);
        }

        return countryRepository.save(new Country(country.getName(),
                slug.toString()
                ,country.getDescription(),country.getImageUrl()));
    }

    @Override
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public void deleteAllCountries() {
        countryRepository.deleteAll();
    }
}
