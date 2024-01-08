package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.repository.UserRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{


   private final UserRepository userRepository;

   @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> save(UserDTO user) {

       User user1 = User.builder()
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .staffId(user.getStaffId())
               .emailAddress(user.getEmailAddress())
               .employer(user.getEmployer())
               .mobileNumber(user.getMobileNumber())
               .accountNumber(user.getAccountNumber())
               .destinationTelco(user.getDestinationTelco())
               .salary(user.getSalary())
               .destinationBank(user.getDestinationBank())
               .build();


       Mono<User> users = userRepository.save(user1);

         return users;
    }

//    existingUser.ifPresent(user -> user.setEnabled(false));

    @Override
    public Mono<Void> deleteById(UUID id) {
        return userRepository.deleteById(id);
    }


    public Mono<Object> updateUser(UUID id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setStaffId(user.getStaffId());
                    existingUser.setEmailAddress(user.getEmailAddress());
                    existingUser.setEmployer(user.getEmployer());
                    existingUser.setMobileNumber(user.getMobileNumber());
                    existingUser.setAccountNumber(user.getAccountNumber());
                    existingUser.setSalary(user.getSalary());
                    existingUser.setDestinationTelco(user.getDestinationTelco());
                    existingUser.setDestinationBank(user.getDestinationBank());

                    return userRepository.save(existingUser);
                }
        );
    }


    public Flux<User> addBulkUsers(MultipartFile excelFile) {
        try {
            List<User> users = readUsersFromExcelFile(excelFile);
            return userRepository.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.error(e);
        }
    }

    private List<User> readUsersFromExcelFile(MultipartFile excelFile) throws IOException {
       List<User> users = new ArrayList<>();

       try {
           // Get the workbook and sheet
           Workbook workbook = new XSSFWorkbook(excelFile.getInputStream());
           Sheet sheet = workbook.getSheet("Users");

           // Loop through rows
           for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
               Row row = sheet.getRow(i);
               User user = new User();

               user.setFirstName(row.getCell(0).getStringCellValue());
               user.setLastName(row.getCell(1).getStringCellValue());
               user.setStaffId(row.getCell(2).getStringCellValue());
               user.setEmailAddress(row.getCell(3).getStringCellValue());
               user.setEmployer(row.getCell(4).getStringCellValue());
               user.setMobileNumber(row.getCell(5).getStringCellValue());
               user.setAccountNumber(row.getCell(6).getStringCellValue());
               user.setDestinationBank(row.getCell(7).getStringCellValue());
               user.setSalary(row.getCell(8).getStringCellValue());
               user.setDestinationTelco(row.getCell(9).getStringCellValue());

               users.add(user);
           }

           workbook.close();
       } catch (IOException e) {
           throw new RuntimeException("FAIL! -> message = " + e.getMessage());

       }
        return users;
    }


//    public User getUserById(Integer id) {
//
//        return userRepository.findById(id).get();
//    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User createUser(User user) {
//        user.setEnabled(true);
//        return userRepository.save(user);
//    }
//
//    public User updateUser(Integer id, User user) {
//
//        Optional<User> existingUser = userRepository.findById(id);
//        if(existingUser.isPresent()) {
//            User updatedUser = existingUser.get();
//            updatedUser.setFirstName(user.getFirstName());
//            updatedUser.setLastName(user.getLastName());
//            updatedUser.setstaffId(user.getstaffId());
//            updatedUser.setEmailAddress(user.getEmailAddress());
//            updatedUser.setEmployer(user.getEmployer());
//            updatedUser.setMobileNumber(user.getMobileNumber());
//            updatedUser.setAccountNumber(user.getAccountNumber());
//
//            return userRepository.save(updatedUser);
//        } else {
//            return null;
//        }
//
//    }
//
//    public void deleteUser(Integer id) {
//
//        Optional<User> existingUser = userRepository.findById(id);
//        existingUser.ifPresent(user -> user.setEnabled(false));
//
//        userRepository.save(existingUser.get());
//
//    }


}


//    public Mono<User> updateUser(UUID id, User user) {
//        return userRepository.findById(id)
//                .flatMap(existingUser -> {
//                    existingUser.setFirstName(user.getFirstName());
//                    existingUser.setLastName(user.getLastName());
//                    existingUser.setstaffId(user.getstaffId());
//                    existingUser.setEmailAddress(user.getEmailAddress());
//                    existingUser.setEmployer(user.getEmployer());
//                    existingUser.setMobileNumber(user.getMobileNumber());
//                    existingUser.setAccountNumber(user.getAccountNumber());
//
//                    return userRepository.save(existingUser);
//                }
//        );