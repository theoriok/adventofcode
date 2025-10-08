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
    
    public View() {
        Select<Integer> yearSelect = new Select<>();
        yearSelect.setLabel("Year");
        yearSelect.setItems(2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024);
        
        Select<Integer> daySelect = new Select<>();
        daySelect.setLabel("Day");
        daySelect.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);
        
        Button submitButton = new Button("Submit", e -> {
            if (yearSelect.getValue() != null && daySelect.getValue() != null) {
                callController(yearSelect.getValue(), daySelect.getValue());
            }
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
