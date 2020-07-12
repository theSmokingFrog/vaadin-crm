package dev.nhoffmann.vaadin.crm.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.nhoffmann.vaadin.crm.backend.entity.Company;
import dev.nhoffmann.vaadin.crm.backend.repository.CompanyRepository;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

}
