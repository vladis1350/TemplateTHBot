package by.minilooth.telegrambot.service;

import by.minilooth.telegrambot.bot.state.admin.AdminBotState;
import by.minilooth.telegrambot.bot.state.client.ClientBotState;
import by.minilooth.telegrambot.model.Admin;
import by.minilooth.telegrambot.model.Client;
import by.minilooth.telegrambot.model.User;
import by.minilooth.telegrambot.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired private AdminRepository adminRepository;

    @Transactional
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    @Transactional
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }

    @Transactional
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin createAdmin(User user) {
        Admin admin = Admin.builder()
                .adminBotState(AdminBotState.getInitialState())
                .user(user)
                .build();

        user.setAdmin(admin);

        return admin;
    }
}
