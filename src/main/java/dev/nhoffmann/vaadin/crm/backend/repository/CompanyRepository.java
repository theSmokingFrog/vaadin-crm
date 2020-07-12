package dev.nhoffmann.vaadin.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.nhoffmann.vaadin.crm.backend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>
{
}
