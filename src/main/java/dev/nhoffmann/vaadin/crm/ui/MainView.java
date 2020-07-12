package dev.nhoffmann.vaadin.crm.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import dev.nhoffmann.vaadin.crm.backend.entity.Company;
import dev.nhoffmann.vaadin.crm.backend.entity.Contact;
import dev.nhoffmann.vaadin.crm.backend.service.ContactService;

@Route
public class MainView extends VerticalLayout
{
    private final ContactService contactService;

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();

    public MainView(final ContactService pContactService)
    {
        contactService = pContactService;
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();

        add(filterText, grid);
        updateGrid();
    }

    private void configureFilter()
    {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(valueChange -> updateGrid());
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
        grid.setItems(contactService.findAll(filterText.getValue()));
    }
}
