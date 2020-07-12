package dev.nhoffmann.vaadin.crm.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import dev.nhoffmann.vaadin.crm.backend.entity.Company;
import dev.nhoffmann.vaadin.crm.backend.entity.Contact;
import dev.nhoffmann.vaadin.crm.backend.service.CompanyService;
import dev.nhoffmann.vaadin.crm.backend.service.ContactService;

@Route
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout
{
    private final ContactService contactService;
    private final CompanyService companyService;

    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();
    private ContactForm form;

    public MainView(final ContactService pContactService, final CompanyService pService)
    {
        contactService = pContactService;
        companyService = pService;
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();

        form = new ContactForm(companyService.findAll());
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);
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
