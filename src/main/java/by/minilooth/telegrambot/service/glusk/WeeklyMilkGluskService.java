package by.minilooth.telegrambot.service.glusk;

import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import by.minilooth.telegrambot.model.glusk.WeeklyMilkGlusk;
import by.minilooth.telegrambot.repositories.glusk.WeeklyMilkGluskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeeklyMilkGluskService {
    @Autowired
    private WeeklyMilkGluskRepository weeklyMilkGluskRepository;
    @Autowired
    private MilkGluskService milkGluskService;

    @Transactional
    public void save(WeeklyMilkGlusk weeklyMilkGlusk) {
        weeklyMilkGluskRepository.save(weeklyMilkGlusk);
    }

    @Transactional
    public void delete(WeeklyMilkGlusk weeklyMilkGlusk) {
        weeklyMilkGluskRepository.delete(weeklyMilkGlusk);
    }

    @Transactional
    public List<WeeklyMilkGlusk> getAll() {
        return weeklyMilkGluskRepository.findAll();
    }

    @Transactional
    public WeeklyMilkGlusk checkWeeklyMilk() {
        WeeklyMilkGlusk weeklyMilkGlusk = weeklyMilkGluskRepository.getWeeklyMilkGluskByDate(LocalDate.now());
        if (weeklyMilkGlusk != null) {
            return weeklyMilkGlusk;
        } else return createWeeklyMilk();

    }

    @Transactional
    public WeeklyMilkGlusk createWeeklyMilk() {
        List<MilkGlusk> milkGluskList = milkGluskService.getMilkByPeriod(8, 0);
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(7);
        WeeklyMilkGlusk lastWeekly = weeklyMilkGluskRepository.getWeeklyMilkGluskByDate(localDate);
        WeeklyMilkGlusk weeklyMilkGlusk = new WeeklyMilkGlusk();
        weeklyMilkGlusk.setDate(LocalDate.now());
        int production = 0;
        int implement = 0;
        double marketability = 0.0;
        double milkOnHead = 0.0;
        if (!milkGluskList.isEmpty()) {
            for (MilkGlusk milkGlusk : milkGluskList) {
                production += milkGlusk.getProduction();
                implement += milkGlusk.getImplement();
                marketability += weeklyMilkParser(milkGlusk.getMarketability());
                milkOnHead += weeklyMilkParser(milkGlusk.getMilkOnHead());
            }
            if (lastWeekly != null) {
                weeklyMilkGlusk.setLastWeeklyDifferenceProd(production - lastWeekly.getWeeklyProduction());
                weeklyMilkGlusk.setLastWeeklyDifferenceImpl(implement - lastWeekly.getWeeklyImplement());
                weeklyMilkGlusk.setLastWeeklyMilkOnHead(String.valueOf(milkOnHead - weeklyMilkParser(lastWeekly.getWeeklyMilkOnHead())));
            } else {
                weeklyMilkGlusk.setLastWeeklyDifferenceProd(0);
                weeklyMilkGlusk.setLastWeeklyDifferenceImpl(0);
                weeklyMilkGlusk.setLastWeeklyMilkOnHead(String.valueOf(0));
            }
            weeklyMilkGlusk.setDate(LocalDate.now());
            weeklyMilkGlusk.setWeeklyProduction(production);
            weeklyMilkGlusk.setWeeklyImplement(implement);
            weeklyMilkGlusk.setWeeklyMarketability(String.valueOf(marketability / milkGluskList.size()));
            weeklyMilkGlusk.setWeeklyMilkOnHead(String.valueOf(milkOnHead));
//            weeklyMilkGlusk.setLastWeeklyMilkOnHead(String.valueOf(0));
            weeklyMilkGlusk.setIsLastWeek(true);
            this.save(weeklyMilkGlusk);
        } else {
            return null;
        }
        return weeklyMilkGlusk;
    }

    private Double weeklyMilkParser(String data) {
        return Double.parseDouble(data.replaceAll(",", "."));
    }

}
