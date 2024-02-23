package com.webapp.service.serviceimpl;

import com.webapp.entity.User;
import com.webapp.repository.UserRepository;
import com.webapp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Override
    public void saveUserData() {
        String curDir = System.getProperty("user.dir");
        String filePath = "/opt/users.csv";
        Path path = Paths.get(curDir, filePath);

        List<User> allUsers = csvToUsers(path.toString());
        for(User user:allUsers){
            if(!userRepository.existsByEmailID(user.getEmailID())){
                userRepository.save(user);
            }
        }
    }

    private List<User> csvToUsers(String inputFile) {
        List<User> users = new ArrayList<User>();

        try {
            Reader input = new FileReader(inputFile);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(new String[]{"first_name", "last_name", "email", "password"})
                    .setSkipHeaderRecord(true)
                    .build();
            Iterable<CSVRecord> records = csvFormat.parse(input);
            for(CSVRecord record: records){
                users.add(
                        new User(
                                record.get(0),  //first name
                                record.get(1),  //last name
                                record.get(2),  //email
                                new BCryptPasswordEncoder().encode(record.get(3))   //password
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
