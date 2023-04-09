package by.minilooth.telegrambot.service.glusk;

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
    public MilkGlusk getMilkByDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        MilkGlusk milkGlusk = milkGluskRepository.findMilkByDate(date);
        if (milkGlusk != null) {
            return milkGlusk;
        } else {
            return null;
        }
    }

    @Transactional
    public List<MilkGlusk> getMilkByPeriod(int after, int before) {
        LocalDate dateAfter = LocalDate.now();
        LocalDate dateBefore = LocalDate.now();
        dateAfter = dateAfter.minusDays(after);
        dateBefore = dateBefore.plusDays(before);
        List<MilkGlusk> milkGlusks = milkGluskRepository.getAllByDateAfterAndDateBefore(dateAfter, dateBefore);
        if (!milkGlusks.isEmpty()) {
            return milkGlusks;
        } else {
            return null;
        }
    }

    @Transactional
    public MilkGlusk checkMilk(List<Object> values) {
        MilkGlusk milkGlusk = milkGluskRepository.findMilkByDate(LocalDate.now());
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
            return createMilk(values);
        }
    }

    @Transactional
    public MilkGlusk createMilk(List<Object> values) {
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
                .build();

        save(milkGlusk);
        return milkGlusk;
    }

    public int parseMilkData(Object data) {
        return Integer.parseInt(data.toString().replaceAll("[^0-9-,]", ""));
    }
}
