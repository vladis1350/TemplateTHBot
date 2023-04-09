package by.minilooth.telegrambot.service.glusk;

import by.minilooth.telegrambot.model.glusk.FieldWorkGlusk;
import by.minilooth.telegrambot.repositories.glusk.FieldWorkGluskRepository;
import by.minilooth.telegrambot.service.SheetsQuickstart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FieldWorkGluskService {
    @Autowired
    private FieldWorkGluskRepository fieldWorkGluskRepository;
    @Autowired
    private SheetsQuickstart sheetsQuickstart;

    @Transactional
    public void save(FieldWorkGlusk fieldWorkGlusk) {
        fieldWorkGluskRepository.save(fieldWorkGlusk);
    }

    @Transactional
    public void delete(FieldWorkGlusk fieldWorkGlusk) {
        fieldWorkGluskRepository.delete(fieldWorkGlusk);
    }

    @Transactional
    public List<FieldWorkGlusk> getAll() {
        return fieldWorkGluskRepository.findAll();
    }

    @Transactional
    public FieldWorkGlusk checkFieldWork(List<Object> values) throws GeneralSecurityException, IOException {
        FieldWorkGlusk fieldWorkGlusk = fieldWorkGluskRepository.findFieldWorkByDate(LocalDate.now());
        List<List<Object>> valuesTopDressing = sheetsQuickstart.getReportAll("Подкормка!J12:S12");
        FieldWorkGlusk yesterday = getFieldWorkByDate(1);
        int introductionOrganic = 0;
        int removalOrganic = 0;
        int introductionToday = 0;
        int removalToday = 0;
        if (yesterday != null) {
            removalOrganic = yesterday.getRemovalOrganic();
            introductionOrganic = yesterday.getIntroductionOrganic();
            introductionToday = Integer.parseInt(values.get(4).toString().replaceAll("[^0-9]", "")) - introductionOrganic;
            removalToday = Integer.parseInt(values.get(2).toString().replaceAll("[^0-9]", "")) - removalOrganic;
        }
        if (fieldWorkGlusk != null) {
            fieldWorkGlusk.setRemovalOrganic(parseFieldWorkData(values.get(2)));
            fieldWorkGlusk.setRemovalOrganicPerDay(removalToday);
            fieldWorkGlusk.setIntroductionOrganic(parseFieldWorkData(values.get(4)));
            fieldWorkGlusk.setIntroductionOrganicPerDay(introductionToday);
            fieldWorkGlusk.setPotassium(parseFieldWorkData(values.get(9)));
            fieldWorkGlusk.setPotassiumPerDay(parseFieldWorkData(values.get(10)));
            fieldWorkGlusk.setPhosphorus(parseFieldWorkData(values.get(13)));
            fieldWorkGlusk.setPhosphorusPerDay(parseFieldWorkData(values.get(14)));
            fieldWorkGlusk.setNitrogen(parseFieldWorkData(values.get(17)));
            fieldWorkGlusk.setNitrogenPerDay(parseFieldWorkData(values.get(18)));
            fieldWorkGlusk.setPlowing(parseFieldWorkData(values.get(21)));
            fieldWorkGlusk.setPlowingPerDay(parseFieldWorkData(values.get(22)));
            fieldWorkGlusk.setDressingNitrogen(parseFieldWorkData(valuesTopDressing.get(0).get(0)));
            fieldWorkGlusk.setDressingNitrogenPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(1)));
            fieldWorkGlusk.setDressingHerbPotassium(parseFieldWorkData(valuesTopDressing.get(0).get(4)));
            fieldWorkGlusk.setDressingHerbPotassiumPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(5)));
            fieldWorkGlusk.setDressingHerbAmmonium(parseFieldWorkData(valuesTopDressing.get(0).get(8)));
            fieldWorkGlusk.setDressingHerbAmmoniumPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(9)));
            return fieldWorkGlusk;
        } else {
            return createFieldWork(values);
        }
    }

    @Transactional
    public FieldWorkGlusk getFieldWorkByDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        FieldWorkGlusk fieldWorkGlusk = fieldWorkGluskRepository.findFieldWorkByDate(date);
        if (fieldWorkGlusk != null) {
            return fieldWorkGlusk;
        } else {
            return null;
        }
    }

    @Transactional
    public List<FieldWorkGlusk> getFieldWorkByPeriod() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(7);
        List<FieldWorkGlusk> fieldWorkGlusks = fieldWorkGluskRepository.getAllByDateAfterAndDateBefore(localDate, LocalDate.now().plusDays(1));
        if (fieldWorkGlusks.isEmpty()) {
            return null;
        } else {
            return fieldWorkGlusks;
        }
    }

    @Transactional
    public FieldWorkGlusk createFieldWork(List<Object> values) throws GeneralSecurityException, IOException {
        List<List<Object>> valuesTopDressing = sheetsQuickstart.getReportAll("Подкормка!J12:S12");
        FieldWorkGlusk yesterday = getFieldWorkByDate(1);
        int introductionOrganic = 0;
        int removalOrganic = 0;
        int introductionToday = 0;
        int removalToday = 0;
        if (yesterday != null) {
            removalOrganic = yesterday.getRemovalOrganic();
            introductionOrganic = yesterday.getIntroductionOrganic();
            introductionToday = Integer.parseInt(values.get(4).toString().replaceAll("[^0-9]", "")) - introductionOrganic;
            removalToday = Integer.parseInt(values.get(2).toString().replaceAll("[^0-9]", "")) - removalOrganic;
        }
        FieldWorkGlusk fieldWorkGlusk = FieldWorkGlusk.builder()
                .date(LocalDate.now())
                .removalOrganic(parseFieldWorkData(values.get(2)))
                .removalOrganicPerDay(removalToday)
                .introductionOrganic(parseFieldWorkData(values.get(4)))
                .introductionOrganicPerDay(introductionToday)
                .potassium(parseFieldWorkData(values.get(9)))
                .potassiumPerDay(parseFieldWorkData(values.get(10)))
                .phosphorus(parseFieldWorkData(values.get(13)))
                .phosphorusPerDay(parseFieldWorkData(values.get(14)))
                .nitrogen(parseFieldWorkData(values.get(17)))
                .nitrogenPerDay(parseFieldWorkData(values.get(18)))
                .plowing(parseFieldWorkData(values.get(21)))
                .plowingPerDay(parseFieldWorkData(values.get(22)))
                .dressingNitrogen(parseFieldWorkData(valuesTopDressing.get(0).get(0)))
                .dressingNitrogenPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(1)))
                .dressingHerbPotassium(parseFieldWorkData(valuesTopDressing.get(0).get(4)))
                .dressingHerbPotassiumPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(5)))
                .dressingHerbAmmonium(parseFieldWorkData(valuesTopDressing.get(0).get(8)))
                .dressingHerbAmmoniumPerDay(parseFieldWorkData(valuesTopDressing.get(0).get(9)))
                .build();

        save(fieldWorkGlusk);
        return fieldWorkGlusk;
    }

    public int parseFieldWorkData(Object data) {
        return Integer.parseInt(data.toString().replaceAll("[^0-9]", ""));
    }

}
