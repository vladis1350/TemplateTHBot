package by.minilooth.telegrambot.bot.message.client;

import by.minilooth.telegrambot.bot.api.MessageSender;
import by.minilooth.telegrambot.bot.context.client.ClientBotContext;
import by.minilooth.telegrambot.bot.keyboard.client.ClientInlineKeyboardSource;
import by.minilooth.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.minilooth.telegrambot.bot.message.MessageService;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.enums.Districts;
import by.minilooth.telegrambot.model.enums.TypeReport;
import by.minilooth.telegrambot.model.glusk.FieldWorkGlusk;
import by.minilooth.telegrambot.model.glusk.MilkGlusk;
import by.minilooth.telegrambot.model.glusk.SowingGlusk;
import by.minilooth.telegrambot.model.glusk.WeeklyMilkGlusk;
import by.minilooth.telegrambot.service.glusk.FieldWorkGluskService;
import by.minilooth.telegrambot.service.glusk.MilkGluskService;
import by.minilooth.telegrambot.service.SheetsQuickstart;
import by.minilooth.telegrambot.service.glusk.SowingGluskService;
import by.minilooth.telegrambot.service.glusk.WeeklyMilkGluskService;
import by.minilooth.telegrambot.util.BotUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClientMessageService extends MessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientMessageService.class);

    @Autowired
    private BotUtils botUtils;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private ClientMessageSource clientMessageSource;
    @Autowired
    private ClientReplyKeyboardMarkupSource clientReplyKeyboardMarkupSource;
    @Autowired
    private ClientInlineKeyboardSource clientInlineKeyboardSource;
    @Autowired
    private MilkGluskService milkGluskService;
    @Autowired
    private SheetsQuickstart sheetsQuickstart;
    @Autowired
    private FieldWorkGluskService fieldWorkGluskService;
    @Autowired
    private WeeklyMilkGluskService weeklyMilkGluskService;
    @Autowired
    private SowingGluskService sowingGluskService;


    @SneakyThrows
    private Boolean checkEditableMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();
        return client.getUser().hasLastBotMessage() && client.getUser().getBotLastMessageEditable() &&
                !messageSender.isMessageExpired(client.getUser().getBotLastMessageDate());
    }

    @SneakyThrows
    public void sendStartMessage(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId(), String.format(clientMessageSource.getMessage("startMessage"), client.getUser().getFirstname()), null);

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    @SneakyThrows
    public void sendMainMenu(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId(), clientMessageSource.getMessage("MainMenu"),
                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendMainMenu to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendSelectReport(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId(), clientMessageSource.getMessage("SelectReport"),
                        clientInlineKeyboardSource.getSelectReportInlineMarkup());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendSelectReport to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendReportMilk(ClientBotContext clientBotContext) throws GeneralSecurityException, IOException {
        Client client = clientBotContext.getClient();

        List<List<Object>> values = sheetsQuickstart.getReportMilk(client);
        List<List<Object>> valuesMilkSort = sheetsQuickstart.getReportAll("МТС!B15:B17");
        MilkGlusk milkGlusk = milkGluskService.checkMilk(values.get(0), client);

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {
        } else {
            try {
                String milkSort = String.format(clientMessageSource.getMessage("milkSort"),
                        valuesMilkSort.get(0).get(0),
                        valuesMilkSort.get(1).get(0),
                        valuesMilkSort.get(2).get(0));
                Message message = messageSender.sendMessage(client.getTelegramId(), String.format(clientMessageSource.getMessage("ReportMilk"), milkGlusk.getProduction(),
                                milkGlusk.getDifferenceProd(), milkGlusk.getImplement(), milkSort,
                                milkGlusk.getDifferenceImpl(),
                                milkGlusk.getMarketability(),
                                milkGlusk.getMilkOnHead()),
                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendReportMilk to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendReportField(ClientBotContext clientBotContext) throws GeneralSecurityException, IOException, ParseException {
        Client client = clientBotContext.getClient();

        List<List<Object>> valuesField = sheetsQuickstart.getReportField(client);
        List<List<Object>> valuesSpringCropsSown = sheetsQuickstart.getSpringCropsSown(client);
        FieldWorkGlusk fieldWorkGlusk = fieldWorkGluskService.checkFieldWork(valuesField.get(0), client);
        SowingGlusk sowingGlusk = sowingGluskService.checkSowingGlusk(valuesSpringCropsSown.get(0), client);

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {
        } else {
            try {
                System.out.println(valuesField.get(0).get(5));
                String field = String.format(clientMessageSource.getMessage("ReportCropProduction"),
                        fieldWorkGlusk.getRemovalOrganic(),
                        fieldWorkGlusk.getRemovalOrganicPerDay(),
                        fieldWorkGlusk.getIntroductionOrganic(),
                        fieldWorkGlusk.getIntroductionOrganicPerDay(),
                        valuesField.get(0).get(5),
                        fieldWorkGlusk.getPotassium(),
                        fieldWorkGlusk.getPotassiumPerDay(),
                        valuesField.get(0).get(11),
                        fieldWorkGlusk.getPhosphorus(),
                        fieldWorkGlusk.getPhosphorusPerDay(),
                        valuesField.get(0).get(15),
                        fieldWorkGlusk.getNitrogen(),
                        fieldWorkGlusk.getNitrogenPerDay(),
                        valuesField.get(0).get(19),
                        fieldWorkGlusk.getPlowing(),
                        fieldWorkGlusk.getPlowingPerDay(),
                        valuesField.get(0).get(23),
                        fieldWorkGlusk.getDressingHerbPotassium(),
                        fieldWorkGlusk.getDressingHerbPotassiumPerDay(),
                        fieldWorkGlusk.getDressingNitrogen(),
                        fieldWorkGlusk.getDressingNitrogenPerDay(),
                        fieldWorkGlusk.getDressingHerbAmmonium(),
                        fieldWorkGlusk.getDressingHerbAmmoniumPerDay());
                String springCropsSown = String.format(clientMessageSource.getMessage("SpringCropsSown"),
                        Integer.parseInt(valuesSpringCropsSown.get(0).get(2).toString().replaceAll("[^0-9-,]", "")) + sowingGlusk.getPeas(),
                        Integer.parseInt(valuesSpringCropsSown.get(0).get(3).toString().replaceAll("[^0-9-,]", "")) + sowingGlusk.getPeasPerDay(),
                        valuesSpringCropsSown.get(0).get(4),//
                        sowingGlusk.getBarley(),
                        sowingGlusk.getBarleyPerDay(),
                        valuesSpringCropsSown.get(0).get(8),//
                        sowingGlusk.getWheat(),
                        sowingGlusk.getWheatPerDay(),
                        valuesSpringCropsSown.get(0).get(12),//
                        sowingGlusk.getTriticale(),
                        sowingGlusk.getTriticalePerDay(),
                        valuesSpringCropsSown.get(0).get(16),//
                        sowingGlusk.getOat(),
                        sowingGlusk.getOatPerDay(),
                        valuesSpringCropsSown.get(0).get(20),//
                        sowingGlusk.getAnnualHerbs(),
                        sowingGlusk.getAnnualHerbsPerDay(),
                        valuesSpringCropsSown.get(0).get(40),//
                        sowingGlusk.getPeas(),
                        sowingGlusk.getPeasPerDay(),
                        valuesSpringCropsSown.get(0).get(36));//
                String report = getReportName(client);
                Message message = messageSender.sendMessage(client.getTelegramId(), report + "\n\n" + field + "\n\n" + springCropsSown,
                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendReportField to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendFullReport(ClientBotContext clientBotContext) throws GeneralSecurityException, IOException, ParseException {
        Client client = clientBotContext.getClient();

        List<List<Object>> valuesMilk = sheetsQuickstart.getReportMilk(client);
        List<List<Object>> valuesField = sheetsQuickstart.getReportField(client);
        List<List<Object>> valuesSpringCropsSown = sheetsQuickstart.getSpringCropsSown(client);
        List<List<Object>> valuesMilkSort = sheetsQuickstart.getReportAll("МТС!B15:B17");
        List<List<Object>> valuesCompoundFeed = sheetsQuickstart.getReportAll("МТС!B8:B13");
        List<List<Object>> valuesMTC = sheetsQuickstart.getReportAll("МТС!A1:B6");
        List<List<Object>> valuesFuel = sheetsQuickstart.getReportAll("МТС!B31:C31");
        FieldWorkGlusk fieldWorkGlusk = fieldWorkGluskService.checkFieldWork(valuesField.get(0), client);
        SowingGlusk sowingGlusk = sowingGluskService.checkSowingGlusk(valuesSpringCropsSown.get(0), client);

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {
        } else {
            try {
                StringBuilder techMTC = new StringBuilder("\n");
                for (List item : valuesMTC) {
                    if (item.size() > 1) {
                        techMTC.append(item.get(0)).append(" ").append(item.get(1)).append("\n");
                    }
                }
                String fuel = String.format(clientMessageSource.getMessage("fuel"),
                        valuesFuel.get(0).get(0),
                        (int) (Integer.parseInt(valuesFuel.get(0).get(1).toString()) * 0.45));
                String reportMTC = String.format(clientMessageSource.getMessage("MtcReport"), techMTC);
                String compoundFeed = String.format(clientMessageSource.getMessage("CompoundFeed"),
                        valuesCompoundFeed.get(0).get(0),
                        valuesCompoundFeed.get(1).get(0),
                        valuesCompoundFeed.get(2).get(0),
                        valuesCompoundFeed.get(3).get(0),
                        valuesCompoundFeed.get(4).get(0),
                        valuesCompoundFeed.get(5).get(0));
                String milkSort = String.format(clientMessageSource.getMessage("milkSort"),
                        valuesMilkSort.get(0).get(0),
                        valuesMilkSort.get(1).get(0),
                        valuesMilkSort.get(2).get(0));
                String springCropsSown = String.format(clientMessageSource.getMessage("SpringCropsSown"),
                        valuesSpringCropsSown.get(0).get(2),
                        valuesSpringCropsSown.get(0).get(3),
                        valuesSpringCropsSown.get(0).get(4),//
                        sowingGlusk.getBarley(),
                        sowingGlusk.getBarleyPerDay(),
                        valuesSpringCropsSown.get(0).get(8),//
                        sowingGlusk.getWheat(),
                        sowingGlusk.getWheatPerDay(),
                        valuesSpringCropsSown.get(0).get(12),//
                        sowingGlusk.getTriticale(),
                        sowingGlusk.getTriticalePerDay(),
                        valuesSpringCropsSown.get(0).get(16),//
                        sowingGlusk.getOat(),
                        sowingGlusk.getOatPerDay(),
                        valuesSpringCropsSown.get(0).get(20),//
                        sowingGlusk.getAnnualHerbs(),
                        sowingGlusk.getAnnualHerbsPerDay(),
                        valuesSpringCropsSown.get(0).get(40),//
                        sowingGlusk.getPeas(),
                        sowingGlusk.getPeasPerDay(),
                        valuesSpringCropsSown.get(0).get(36));//
                String milk = String.format(clientMessageSource.getMessage("ReportMilk"), valuesMilk.get(0).get(4),
                        valuesMilk.get(0).get(7),
                        valuesMilk.get(0).get(10),
                        milkSort,
                        valuesMilk.get(0).get(12),
                        valuesMilk.get(0).get(13),
                        valuesMilk.get(0).get(19));
                String field = String.format(clientMessageSource.getMessage("ReportCropProduction"), fieldWorkGlusk.getRemovalOrganic(),
                        fieldWorkGlusk.getRemovalOrganicPerDay(),
                        fieldWorkGlusk.getIntroductionOrganic(),
                        fieldWorkGlusk.getIntroductionOrganicPerDay(),
                        fieldWorkGlusk.getPotassium(),
                        fieldWorkGlusk.getPotassiumPerDay(),
                        fieldWorkGlusk.getPhosphorus(),
                        fieldWorkGlusk.getPhosphorusPerDay(),
                        fieldWorkGlusk.getNitrogen(),
                        fieldWorkGlusk.getNitrogenPerDay(),
                        fieldWorkGlusk.getPlowing(),
                        fieldWorkGlusk.getPlowingPerDay(),
                        fieldWorkGlusk.getDressingNitrogen(),
                        fieldWorkGlusk.getDressingNitrogenPerDay(),
                        fieldWorkGlusk.getDressingHerbPotassium(),
                        fieldWorkGlusk.getDressingHerbPotassiumPerDay(),
                        fieldWorkGlusk.getDressingHerbAmmonium(),
                        fieldWorkGlusk.getDressingHerbAmmoniumPerDay());
                String report = getReportName(client);
                Message message = messageSender.sendMessage(client.getTelegramId(), report + "\n\n" + field + "\n\n" + springCropsSown + "\n\n" + milk + "\n\n" + compoundFeed +
                                "\n\n" + fuel + "\n\n" + reportMTC,
                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendFullReport to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendWeeklyReport(ClientBotContext clientBotContext) throws GeneralSecurityException, IOException, ParseException {
        Client client = clientBotContext.getClient();

        FieldWorkGlusk fieldWorkGluskWeekly = fieldWorkGluskService.getFieldWorkByDate(8, client.getDistricts());
        FieldWorkGlusk fieldWorkGluskDaily = fieldWorkGluskService.getFieldWorkByDate(0, client.getDistricts());

        WeeklyMilkGlusk weeklyMilkGlusk = weeklyMilkGluskService.checkWeeklyMilk(client);

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {
        } else {
            try {
                String milk = "";
                if (weeklyMilkGlusk != null) {
                    milk = String.format(clientMessageSource.getMessage("ReportMilkWeekly"),
                            weeklyMilkGlusk.getWeeklyProduction(),
                            weeklyMilkGlusk.getLastWeeklyDifferenceProd(),
                            weeklyMilkGlusk.getWeeklyImplement(),
                            weeklyMilkGlusk.getLastWeeklyDifferenceImpl(),
                            weeklyMilkGlusk.getWeeklyMarketability(),
                            weeklyMilkGlusk.getWeeklyMilkOnHead(),
                            weeklyMilkGlusk.getLastWeeklyMilkOnHead());
                } else {
                    milk = "Не верные данные";
                }
//                        Double.parseDouble(milkDaily.getMilkOnHeadBeginningMonth().replaceAll(",",".")) - Double.parseDouble(milkWeekly.getMilkOnHeadBeginningMonth().replaceAll(",",".")));
                String field = String.format(clientMessageSource.getMessage("ReportCropProductionWeekly"),
                        fieldWorkGluskDaily.getRemovalOrganic(),
                        fieldWorkGluskDaily.getRemovalOrganic() - fieldWorkGluskWeekly.getRemovalOrganic(),
                        fieldWorkGluskDaily.getIntroductionOrganic(),
                        fieldWorkGluskDaily.getIntroductionOrganic() - fieldWorkGluskWeekly.getIntroductionOrganic(),
                        fieldWorkGluskDaily.getPotassium(),
                        fieldWorkGluskDaily.getPotassium() - fieldWorkGluskWeekly.getPotassium(),
                        fieldWorkGluskDaily.getPhosphorus(),
                        fieldWorkGluskDaily.getPhosphorus() - fieldWorkGluskWeekly.getPhosphorus(),
                        fieldWorkGluskDaily.getNitrogen(),
                        fieldWorkGluskDaily.getNitrogen() - fieldWorkGluskWeekly.getNitrogen(),
                        fieldWorkGluskDaily.getPlowing(),
                        fieldWorkGluskDaily.getPlowing() - fieldWorkGluskWeekly.getPlowing(),
                        fieldWorkGluskDaily.getDressingNitrogen(),
                        fieldWorkGluskDaily.getDressingNitrogen() - fieldWorkGluskWeekly.getDressingNitrogen(),
                        fieldWorkGluskDaily.getDressingHerbPotassium(),
                        fieldWorkGluskDaily.getDressingHerbPotassium() - fieldWorkGluskWeekly.getDressingHerbPotassium(),
                        fieldWorkGluskDaily.getDressingHerbAmmonium(),
                        fieldWorkGluskDaily.getDressingHerbAmmonium() - fieldWorkGluskWeekly.getDressingHerbAmmonium());
                LocalDate date = LocalDate.now(); // получаем текущую дату
                String currentDate = date.getDayOfMonth() - 1 + "." + date.getMonthValue() + "." + date.getYear();
                date = date.minusDays(7);
                String currentWeeklyDate = date.getDayOfMonth() + "." + date.getMonthValue() + "." + date.getYear();
                String report = String.format(clientMessageSource.getMessage("REPORTWeekly"), currentWeeklyDate, currentDate);
                Message message = messageSender.sendMessage(client.getTelegramId(), report + "\n\n" + milk + "\n\n" + field + "\n\n",
                        clientReplyKeyboardMarkupSource.getMainMenuKeyboard());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendFullReport to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendSelectDistrict(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        messageSender.deleteBotLastMessage(client.getUser());
        if (checkEditableMessage(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId(), clientMessageSource.getMessage("SelectDistrict"),
                        clientInlineKeyboardSource.getSelectDistrictInlineMarkup());

                updateLastBotMessageId(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to sendSelectDistrict to user: {}, reason: {}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public String getReportName(Client client) {
        LocalDate date = LocalDate.now(); // получаем текущую дату
        String currentDate = date.getDayOfMonth() + ".0" + date.getMonthValue() + "." + date.getYear();
        String report = currentDate;
        if (client.getDistricts().equals(Districts.GLUSK)) {
            if (client.getTypeReport().equals(TypeReport.FULL)) {
                return String.format(clientMessageSource.getMessage("REPORTDailyGlusk"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.MILK)) {
                return String.format(clientMessageSource.getMessage("REPORTMilkGlusk"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.FIELD)) {
                return String.format(clientMessageSource.getMessage("REPORTFieldGlusk"), currentDate);
            }
        } else if (client.getDistricts().equals(Districts.BOBRUISK)) {
            if (client.getTypeReport().equals(TypeReport.FULL)) {
                return String.format(clientMessageSource.getMessage("REPORTDailyBobr"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.MILK)) {
                return String.format(clientMessageSource.getMessage("REPORTMilkBobr"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.FIELD)) {
                return String.format(clientMessageSource.getMessage("REPORTFieldBobr"), currentDate);
            }
        } else if (client.getDistricts().equals(Districts.OSIPOVICHI)) {
            if (client.getTypeReport().equals(TypeReport.FULL)) {
                return String.format(clientMessageSource.getMessage("REPORTDailyOsip"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.MILK)) {
                return String.format(clientMessageSource.getMessage("REPORTMilkOsip"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.FIELD)) {
                return String.format(clientMessageSource.getMessage("REPORTFieldOsip"), currentDate);
            }
        } else if (client.getDistricts().equals(Districts.PUHOVICHI)) {
            if (client.getTypeReport().equals(TypeReport.FULL)) {
                return String.format(clientMessageSource.getMessage("REPORTDailyPuh"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.MILK)) {
                return String.format(clientMessageSource.getMessage("REPORTMilkPuh"), currentDate);
            } else if (client.getTypeReport().equals(TypeReport.FIELD)) {
                return String.format(clientMessageSource.getMessage("REPORTFieldPuh"), currentDate);
            }
        }
        return report;
    }
}
