package by.minilooth.telegrambot.service;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.enums.Districts;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "generateReport";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public List<List<Object>> getSpringCropsSown(Client client) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        final String spreadsheetId = "17rWCf0nVbvP-8M1fwI3C3a5VAJOCttlIzVJEt5d9n9I";
        final String spreadsheetId = "1vbly904lHBysQxutUFrFUOjokeXTN2GbhqYA4zG9gU8";
        String range = "";
        if (client.getDistricts().equals(Districts.GLUSK)) {
            range = "Сев яровых!A10:AO10";
        } else if (client.getDistricts().equals(Districts.BOBRUISK)) {
            range = "Сев яровых!A16:AO16";
        } else if (client.getDistricts().equals(Districts.OSIPOVICHI)) {
            range = "Сев яровых!A23:AO23";
        } else if(client.getDistricts().equals(Districts.PUHOVICHI)) {
            range = "Сев яровых!A32:AO32";
        }
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            return null;
        } else {
            return values;
        }
    }
    public List<List<Object>> getReportField(Client client) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1vbly904lHBysQxutUFrFUOjokeXTN2GbhqYA4zG9gU8";
//        https://docs.google.com/spreadsheets/d/1PsLtqIknKq_wPoOwy8cm7-KJE-GUAMwcf4qquTTwp8E/edit?usp=sharing
//        final String spreadsheetId = "1PsLtqIknKq_wPoOwy8cm7-KJE-GUAMwcf4qquTTwp8E";
        String range = "";
        if (client.getDistricts().equals(Districts.GLUSK)) {
            range = "Внесение!A11:X11";
        } else if (client.getDistricts().equals(Districts.BOBRUISK)) {
            range = "Внесение!A17:X17";
        } else if (client.getDistricts().equals(Districts.OSIPOVICHI)) {
            range = "Внесение!A24:X24";
        } else if(client.getDistricts().equals(Districts.PUHOVICHI)) {
            range = "Внесение!A33:X33";
        }
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            return null;
        } else {
            return values;
        }
    }

    public List<List<Object>> getReportMilk(Client client) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1n3uaVpR2dzQww7Whgtxep2xWAK3yKHWcCqSELk4mjFg";
//        final String spreadsheetId = "14PCOAT4K-NDzqPzYpEZDWO3ocTxt5OZb1TK7L2TShIQ";
        String range = "";
        if (client.getDistricts().equals(Districts.GLUSK)) {
            range = "Молоко!A13:T13";
        } else if (client.getDistricts().equals(Districts.BOBRUISK)) {
            range = "Молоко!A19:T19";
        } else if (client.getDistricts().equals(Districts.OSIPOVICHI)) {
            range = "Молоко!A26:T26";
        } else if(client.getDistricts().equals(Districts.PUHOVICHI)) {
            range = "Молоко!A35:T35";
        }
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            return null;
        } else {
            return values;
        }
    }

    public List<List<Object>> getReportAll(String range) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1vbly904lHBysQxutUFrFUOjokeXTN2GbhqYA4zG9gU8";
//        final String range = "МТС!A15:B17";
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            return null;
        } else {
            return values;
        }
    }

}
