package by.minilooth.telegrambot.service.glusk;

import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import by.minilooth.telegrambot.model.glusk.SowingGlusk;
import by.minilooth.telegrambot.repositories.glusk.SowingGluskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SowingGluskService {
    @Autowired private SowingGluskRepository sowingGluskRepository;
    @Transactional
    public void save(SowingGlusk milkGlusk) {
        sowingGluskRepository.save(milkGlusk);
    }

    @Transactional
    public void delete(SowingGlusk milkGlusk) {
        sowingGluskRepository.delete(milkGlusk);
    }

    @Transactional
    public List<SowingGlusk> getAll() {
        return sowingGluskRepository.findAll();
    }

    @Transactional
    public SowingGlusk getSowingGluskByDate(int days, Client client) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        SowingGlusk sowingGlusk = sowingGluskRepository.findSowingGluskByDateAndDistrict(date, client.getDistricts());
        if (sowingGlusk != null) {
            return sowingGlusk;
        } else {
            return null;
        }
    }

    @Transactional
    public SowingGlusk checkSowingGlusk(List<Object> values, Client client) {
        SowingGlusk sowingGlusk = sowingGluskRepository.findSowingGluskByDateAndDistrict(LocalDate.now(), client.getDistricts());
        if (sowingGlusk != null) {
            sowingGlusk.setBarley(parseSowingGluskData(values.get(6)));
            sowingGlusk.setBarleyPerDay(parseSowingGluskData(values.get(7)));
            sowingGlusk.setWheat(parseSowingGluskData(values.get(10)));
            sowingGlusk.setWheatPerDay(parseSowingGluskData(values.get(11)));
            sowingGlusk.setTriticale(parseSowingGluskData(values.get(14)));
            sowingGlusk.setTriticalePerDay(parseSowingGluskData(values.get(15)));
            sowingGlusk.setOat(parseSowingGluskData(values.get(18)));
            sowingGlusk.setOatPerDay(parseSowingGluskData(values.get(19)));
            sowingGlusk.setPeas(parseSowingGluskData(values.get(34)));
            sowingGlusk.setPeasPerDay(parseSowingGluskData(values.get(35)));
            sowingGlusk.setAnnualHerbs(parseSowingGluskData(values.get(38)));
            sowingGlusk.setAnnualHerbsPerDay(parseSowingGluskData(values.get(39)));
            return sowingGlusk;
        } else {
            return  createSowingGlusk(values, client);
        }
    }

    @Transactional
    public SowingGlusk createSowingGlusk(List<Object> values, Client client) {
        SowingGlusk sowingGlusk = SowingGlusk.builder()
                .date(LocalDate.now())
                .barley(parseSowingGluskData(values.get(6)))
                .barleyPerDay(parseSowingGluskData(values.get(7)))
                .wheat(parseSowingGluskData(values.get(10)))
                .wheatPerDay(parseSowingGluskData(values.get(11)))
                .triticale(parseSowingGluskData(values.get(14)))
                .triticalePerDay(parseSowingGluskData(values.get(15)))
                .oat(parseSowingGluskData(values.get(18)))
                .oatPerDay(parseSowingGluskData(values.get(19)))
                .peas(parseSowingGluskData(values.get(34)))
                .peasPerDay(parseSowingGluskData(values.get(35)))
                .annualHerbs(parseSowingGluskData(values.get(38)))
                .annualHerbsPerDay(parseSowingGluskData(values.get(39)))
                .district(client.getDistricts())
                .build();

        save(sowingGlusk);
        return sowingGlusk;
    }

    public int parseSowingGluskData(Object data) {
        return Integer.parseInt(data.toString().replaceAll("[^0-9-,]", ""));
    }
}
