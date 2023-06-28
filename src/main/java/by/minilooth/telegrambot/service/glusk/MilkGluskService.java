package by.minilooth.telegrambot.service.glusk;

import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import by.minilooth.telegrambot.repositories.glusk.MilkGluskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MilkGluskService {
    @Autowired private MilkGluskRepository milkGluskRepository;

    @Transactional
    public void save(MilkGlusk milkGlusk) {
        milkGluskRepository.save(milkGlusk);
    }

    @Transactional
    public void delete(MilkGlusk milkGlusk) {
        milkGluskRepository.delete(milkGlusk);
    }

    @Transactional
    public List<MilkGlusk> getAll() {
        return milkGluskRepository.findAll();
    }

    @Transactional
    public MilkGlusk getMilkByDate(int days, Client client) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        MilkGlusk milkGlusk = milkGluskRepository.findMilkByDateAndDistrict(date, client.getDistricts());
        if (milkGlusk != null) {
            return milkGlusk;
        } else {
            return null;
        }
    }

    @Transactional
    public List<MilkGlusk> getMilkByPeriod(int after, int before, Client client) {
        LocalDate dateAfter = LocalDate.now();
        LocalDate dateBefore = LocalDate.now();
        dateAfter = dateAfter.minusDays(after);
        dateBefore = dateBefore.plusDays(before);
        List<MilkGlusk> milkGlusks = milkGluskRepository.getAllByDateAfterAndDateBeforeAndDistrict(dateAfter, dateBefore, client.getDistricts());
        if (!milkGlusks.isEmpty()) {
            return milkGlusks;
        } else {
            return null;
        }
    }

    @Transactional
    public MilkGlusk checkMilk(List<Object> values, Client client) {
        MilkGlusk milkGlusk = milkGluskRepository.findMilkByDateAndDistrict(LocalDate.now(), client.getDistricts());
        if (milkGlusk != null) {
            milkGlusk.setProduction(parseMilkData(values.get(4)));
            milkGlusk.setDifferenceProd(parseMilkData(values.get(7)));
            milkGlusk.setImplement(parseMilkData(values.get(10)));
            milkGlusk.setDifferenceImpl(parseMilkData(values.get(12)));
            milkGlusk.setMarketability(values.get(13).toString().replaceAll("[^0-9-,]", ""));
            milkGlusk.setMilkOnHead(values.get(19).toString().replaceAll("[^0-9-,]", ""));
            milkGlusk.setMilkOnHeadBeginningMonth(values.get(15).toString().replaceAll("[^0-9-,]", ""));
            milkGlusk.setProductionBeginningMonth(values.get(9).toString().replaceAll("[^0-9-,]", ""));
            return milkGlusk;
        } else {
            return createMilk(values, client);
        }
    }

    @Transactional
    public MilkGlusk createMilk(List<Object> values, Client client) {
        MilkGlusk milkGlusk = MilkGlusk.builder()
                .date(LocalDate.now())
                .production(parseMilkData(values.get(4)))
                .differenceProd(parseMilkData(values.get(7)))
                .implement(parseMilkData(values.get(10)))
                .differenceImpl(parseMilkData(values.get(12)))
                .marketability(values.get(13).toString().replaceAll("[^0-9-,]", ""))
                .milkOnHead(values.get(19).toString().replaceAll("[^0-9-,]", ""))
                .milkOnHeadBeginningMonth(values.get(15).toString().replaceAll("[^0-9-,]", ""))
                .productionBeginningMonth(values.get(9).toString().replaceAll("[^0-9-,]", ""))
                .district(client.getDistricts())
                .build();

        save(milkGlusk);
        return milkGlusk;
    }

    public int parseMilkData(Object data) {
        return Integer.parseInt(data.toString().replaceAll("[^0-9-,]", ""));
    }
}
