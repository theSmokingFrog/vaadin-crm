package dev.nhoffmann.vaadin.crm.ui.view.dashboard;

import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import dev.nhoffmann.vaadin.crm.backend.service.CompanyService;
import dev.nhoffmann.vaadin.crm.backend.service.ContactService;
import dev.nhoffmann.vaadin.crm.ui.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Vaadin CRM")
public class DashboardView extends VerticalLayout
{

    private ContactService contactService;
    private CompanyService companyService;

    public DashboardView(ContactService contactService, CompanyService companyService)
    {
        this.contactService = contactService;
        this.companyService = companyService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats()
    {
        Span stats = new Span(contactService.count() + " contacts");
        stats.addClassName("contact-stats");
        return stats;
    }

    private Chart getCompaniesChart()
    {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> companies = companyService.getStats();
        companies.forEach((company, employees) ->
                dataSeries.add(new DataSeriesItem(company, employees)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
