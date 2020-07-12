package dev.nhoffmann.vaadin.crm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.nhoffmann.vaadin.crm.backend.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>
{
}
