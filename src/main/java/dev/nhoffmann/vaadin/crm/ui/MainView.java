package dev.nhoffmann.vaadin.crm.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import dev.nhoffmann.vaadin.crm.backend.entity.Company;
import dev.nhoffmann.vaadin.crm.backend.entity.Contact;
import dev.nhoffmann.vaadin.crm.backend.service.ContactService;

@Route
public class MainView extends VerticalLayout
{
    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);

    public MainView(final ContactService pContactService)
    {
        contactService = pContactService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateGrid();
    }

    private void configureGrid()
    {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateGrid()
    {
        grid.setItems(contactService.findAll());
    }
}
