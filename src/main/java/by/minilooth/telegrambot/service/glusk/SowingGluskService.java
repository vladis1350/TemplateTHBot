package by.minilooth.telegrambot.service.glusk;

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
    public SowingGlusk getSowingGluskByDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        SowingGlusk sowingGlusk = sowingGluskRepository.findSowingGluskByDate(date);
        if (sowingGlusk != null) {
            return sowingGlusk;
        } else {
            return null;
        }
    }

    @Transactional
    public SowingGlusk checkSowingGlusk(List<Object> values) {
        SowingGlusk sowingGlusk = sowingGluskRepository.findSowingGluskByDate(LocalDate.now());
        if (sowingGlusk != null) {
            return sowingGlusk;
        } else {
            return  createSowingGlusk(values);
        }
    }

    @Transactional
    public SowingGlusk createSowingGlusk(List<Object> values) {
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
                .build();

        save(sowingGlusk);
        return sowingGlusk;
    }

    public int parseSowingGluskData(Object data) {
        return Integer.parseInt(data.toString().replaceAll("[^0-9-,]", ""));
    }
}
