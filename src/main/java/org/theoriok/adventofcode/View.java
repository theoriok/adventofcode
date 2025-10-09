package org.theoriok.adventofcode;

import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("fe")
public class View extends VerticalLayout {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Years years = new Years();
    private final Days days = new Days();

    public View() {
        Select<Integer> yearSelect = new Select<>();
        yearSelect.setLabel("Year");
        yearSelect.setItems(years.availableYears());
        Select<Integer> daySelect = new Select<>();
        daySelect.setLabel("Day");
        daySelect.setEnabled(false);
        Button submitButton = new Button("Submit", _ -> callController(yearSelect.getValue(), daySelect.getValue()));
        submitButton.setEnabled(false);
        daySelect.addValueChangeListener(_ -> submitButton.setEnabled(true));
        yearSelect.addValueChangeListener(yearValueChanged -> {
            daySelect.clear();
            daySelect.setItems(days.availableDays(yearValueChanged.getValue()));
            daySelect.setEnabled(true);
            submitButton.setEnabled(false);
        });
        add(yearSelect, daySelect, submitButton);
    }

    private void callController(Integer year, Integer day) {
        try {
            String response = restTemplate.getForObject("http://localhost:8765/%d/%d".formatted(year, day), String.class);
            Notification.show(response).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage()).addThemeVariants(LUMO_ERROR);
        }
    }
}
