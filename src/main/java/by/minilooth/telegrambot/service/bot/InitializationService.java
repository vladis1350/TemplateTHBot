package by.minilooth.telegrambot.service.bot;

import by.minilooth.telegrambot.service.SheetsQuickstart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class InitializationService {
    @Autowired
    public SheetsQuickstart sheetsQuickstart;
    public void initialize() throws GeneralSecurityException, IOException {
//        sheetsQuickstart.mains();

    }

}
