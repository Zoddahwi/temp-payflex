package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.OtpDTO;
import com.fintechedge.payflex.dto.UserDTO;

import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.model.otp.Otp;
import com.fintechedge.payflex.model.setup.Account;
import com.fintechedge.payflex.repository.UserRepository;
import com.fintechedge.payflex.repository.otp.OtpRepository;
import com.fintechedge.payflex.repository.setup.AccountRepository;
import com.fintechedge.payflex.repository.setup.DepartmentRepository;
import com.fintechedge.payflex.service.otp.OtpService;
import com.fintechedge.payflex.service.otp.OtpServiceImp;
import com.fintechedge.payflex.service.otp.VerificationType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;


import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

//    @Autowired
//    private EmailEventProducer emailEventProducer;

    private final DatabaseClient databaseClient;


    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    private final AccountRepository accountRepository;

    private final DepartmentRepository departmentRepository;

    private final OtpRepository otpRepository;

    private final OtpService otpService;


    @Autowired
    public UserServiceImpl(DatabaseClient databaseClient, UserRepository userRepository, AccountRepository accountRepository, DepartmentRepository departmentRepository, OtpRepository otpRepository,
                           OtpService otpService) {
        this.databaseClient = databaseClient;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.otpRepository = otpRepository;
        this.otpService = otpService;
    }

    public Flux<Map<String, Object>> joinUserAndAccount() {

        String query = "SELECT u.*, a.* FROM tbl_user u INNER JOIN tbl_account a ON u.account_id = a.id";
        return databaseClient.sql(query)
                .fetch()
                .all();
    }


    public Mono<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

//    public Mono<User> save(UserDTO user) {
//        LOGGER.info("Saving user: {}", user);
//        return userRepository.save(user.toUser())
//                .doOnSuccess(savedUser -> LOGGER.info("Saved user: {}", savedUser))
//                .doOnError(error -> LOGGER.error("Error saving user: {}", user, error));
//    }

    @Override
    public Mono<User> save(UserDTO userDTO) {
        try {
            Account account = userDTO.getAccount();
            if (account == null) {
                LOGGER.error("Account in UserDTO is null");
                throw new RuntimeException("Account in UserDTO is null");
            }
            return accountRepository.save(account)
                    .doOnNext(savedAccount -> {
                        if (savedAccount == null) {
                            LOGGER.error("Saved account is null");
                            throw new RuntimeException("Saved account is null");
                        }
                    })
                    .flatMap(savedAccount -> {
                        User user = User.builder()
                                .firstName(userDTO.getFirstName())
                                .middleName(userDTO.getMiddleName())
                                .lastName(userDTO.getLastName())
                                .staffId(userDTO.getStaffId())
                                .emailAddress(userDTO.getEmailAddress())
                                .employerId(userDTO.getEmployerId())
                                .mobileNumber(userDTO.getMobileNumber())
                                .salary(userDTO.getSalary())
                                .departmentId(userDTO.getDepartmentId())
                                .emailVerified(userDTO.isEmailVerified())
                                .mobileVerified(userDTO.isMobileVerified())
                                .accountApproved(userDTO.isAccountApproved())
                                .accountId(savedAccount.getId()) // Set the accountId here
                                .build();
                        return userRepository.save(user);
                    })
                    .publishOn(Schedulers.boundedElastic());
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred: ", e);
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
//    @Override
//    public Mono<User> save(UserDTO user) {
//        return userRepository.findByAccountId(user.getAccountId()))
//                .hasElement()
//                .flatMap(accountExists -> {
//                    if (accountExists) {
//                        return Mono.error(new Exception("Account number already exists"));
//                    } else {
//                        User user1 = User.builder()
//                                .firstName(user.getFirstName())
//                                .middleName(user.getMiddleName())
//                                .lastName(user.getLastName())
//                                .staffId(user.getStaffId())
//                                .emailAddress(user.getEmailAddress())
//                                .employerId(user.getEmployerId())
//                                .mobileNumber(user.getMobileNumber())
//                                .salary(user.getSalary())
//                                .accountId(user.getAccountId())
//                                .departmentId(user.getDepartmentId())
//                                .emailVerified(user.isEmailVerified())
//                                .mobileVerified(user.isMobileVerified())
//                                .accountApproved(user.isAccountApproved())
//                                .build();
//
//                        otpService.generateOtpAndSend(user.getEmailAddress());
//
//                        return userRepository.save(user1);
//                    }
//                });
//    }

//    existingUser.ifPresent(user -> user.setEnabled(false));

    @Override
    public Mono<Void> deleteById(UUID id) {
        return userRepository.deleteById(id);
    }


    public Mono<Object> updateUser(UUID id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                            existingUser.setFirstName(user.getFirstName());
                            existingUser.setMiddleName(user.getMiddleName());
                            existingUser.setLastName(user.getLastName());
                            existingUser.setStaffId(user.getStaffId());
                            existingUser.setEmailAddress(user.getEmailAddress());
                            existingUser.setEmployerId(user.getEmployerId());
                            existingUser.setMobileNumber(user.getMobileNumber());
                            existingUser.setAccountId(user.getAccountId());
                            existingUser.setSalary(user.getSalary());
                            existingUser.setDepartmentId(user.getDepartmentId());

                            return userRepository.save(existingUser);
                        }
                );
    }


    public Mono<User> addBulkUsers(MultipartFile excelFile) {
        return readUsersFromExcelFile(excelFile)
                .flatMap(user -> userRepository.save(user))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.error(e);
                });
    }

    public Mono<Boolean> verifyOtp(String userId, String otp) {
        Mono<OtpServiceImp.VerifyOtpResult> otpVerified = otpService.verifyOtp(userId, otp);
        if (otpVerified == null) {
            return userRepository.findById(UUID.fromString(userId)).hasElement()
                    .flatMap(user -> {
                        Boolean.parseBoolean(String.valueOf(true));
                        return userRepository.save(user);
                    })
                    .map(user -> "OTP Sent Successfully").hasElement();
        } else {
            return Mono.just("OTP Verification Failed").hasElement();


        }
    }

//    @Override
//    public Mono<String> verifyOtp(String userId, String otp) {
//        Mono<OtpServiceImp.VerifyOtpResult> otpVerified = otpService.verifyOtp(userId, otp);
//        if (otpVerified) {
//            return userRepository.findById(UUID.fromString(userId))
//                    .flatMap(user -> {
//                        user.setEnabled(true);
//                        return userRepository.save(user);
//                    })
//                    .map(user -> "OTP Sent Successfully");
//        } else {
//            return Mono.just("OTP Verification Failed");
//        }
//
//    }

    @Override
    public Mono<String> generateOtpAndSend(UserDTO user) {
        return otpService.generateOtpAndSend(user.getEmailAddress());
    }


    @Override
    public Mono<User> readUsersFromExcelFile(MultipartFile excelFile) {
        return Mono.fromCallable(() -> {
            User user = new User();

            try {
                // Get the workbook and sheet
                Workbook workbook = new XSSFWorkbook(excelFile.getInputStream());
                Sheet sheet = workbook.getSheet("Users");

                // Loop through rows
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);

                    user.setFirstName(row.getCell(0).getStringCellValue());
                    user.setMiddleName(row.getCell(1).getStringCellValue());
                    user.setLastName(row.getCell(1).getStringCellValue());
                    user.setStaffId(row.getCell(2).getStringCellValue());
                    user.setEmailAddress(row.getCell(3).getStringCellValue());
                    user.setEmployerId(row.getCell(4).getStringCellValue());
                    user.setMobileNumber(row.getCell(5).getStringCellValue());
                    user.setAccountId(UUID.fromString(row.getCell(6).getStringCellValue()));
                    user.setDepartmentId(UUID.fromString(row.getCell(7).getStringCellValue()));
                    user.setSalary(row.getCell(8).getStringCellValue());
                }

                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException("FAIL! -> message = " + e.getMessage());
            }
            return user;
        });
    }

//    @Override
//    public Mono<User> addAccountToUser(UUID userId, Account accountId) {
//        return userRepository.findById(userId)
//                .zipWith(accountRepository.findById(accountId.getId()))
//                .flatMap(tuple -> {
//                    User user = tuple.getT1();
//                    Account account = tuple.getT2();
//                    user.getAccount().add(account);
//                    user.getAccounts().add(account);
//                    return userRepository.save(user);
//                });
//    }

//    public Mono<String> verifyOtp (String userId, String otp) {
//        return otpService.verifyOtp(userId, otp)
//                .flatMap(otpVerified -> {
//                    if(otpVerified) {
//                        return userRepository.findById(userId)
//                                .flatMap(user -> {
//                                    user.setEnabled(true);
//                                    return userRepository.save(user);
//                                })
//                                .map(user -> "OTP Verified Successfully");
//                    } else {
//                        return Mono.just("OTP Verification Failed");
//                    }
//
//                });
//
//    }

//    public Mono<String> verifyOtp (String destination, String otp) {
//        return otpService.verifyOtp(destination, otp)
//                .flatmap(otpVerified -> {
//                    if(otpVerified) {
//                        return userRepository.findByEmailAddress(destination)
//                                .flatMap(user -> {
//                                    user.setEnabled(true);
//                                    return userRepository.save(user);
//                                })
//                                .map(user -> true);
//                    } else {
//                        return Mono.just(false);
//                    }
//
//                });
//    }


    @Override
    public Mono<User> registerUser(UserDTO user) {

        User newUser = User.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .staffId(user.getStaffId())
                .emailAddress(user.getEmailAddress())
                .employerId(user.getEmployerId())
                .mobileNumber(user.getMobileNumber())
                .salary(user.getSalary())
                .departmentId(user.getDepartmentId())
                .build();
        OtpDTO otpDTO = new OtpDTO();
        otpDTO.setEmail(user.getEmailAddress());
        otpDTO.setUserId(String.valueOf(user.getId()));
        otpDTO.setOtp(String.valueOf(otpService.generateOtpAndSend(user.getEmailAddress())));
        otpDTO.setStatus("Pending");
        otpDTO.setType("Email");
        otpDTO.setCode("");
        otpDTO.setVerificationType(VerificationType.valueOf("EMAIL"));
        otpDTO.setExpiryTime(otpDTO.getExpiryTime());
        otpDTO.setMobileNumber(user.getMobileNumber());

        otpService.save(Otp.builder()
                .otp(otpDTO.getOtp())
                .verificationType(otpDTO.getVerificationType())
                .email(otpDTO.getEmail())
                .mobileNumber(otpDTO.getMobileNumber())
                .userId(otpDTO.getUserId())
                .status(otpDTO.getStatus())
                .type(otpDTO.getType())
                .code(otpDTO.getCode())
                .expiryTime(otpDTO.getExpiryTime())
                .build());


        return otpRepository.save(Otp.builder()
                        .otp(otpDTO.getOtp())
                        .verificationType(otpDTO.getVerificationType())
                        .email(otpDTO.getEmail())
                        .mobileNumber(otpDTO.getUserId())
                        .userId(otpDTO.getMobileNumber())
                        .status(otpDTO.getStatus())
                        .type(otpDTO.getType())
                        .code(otpDTO.getCode())
                        .expiryTime(otpDTO.getExpiryTime())
                        .build())
                .flatMap(otp -> {
                    if (otp.getStatus().equals("Pending")) {
                        return otpRepository.save(otp)
                                .flatMap(otp1 -> {
                                    if (otp1.getStatus().equals("Pending")) {
                                        return userRepository.save(newUser);
                                    } else {
                                        return Mono.error(new Exception("OTP Verification Failed"));
                                    }
                                });
                    } else {
                        return Mono.error(new Exception("OTP Verification Failed"));
                    }

                });
    }


//        return userRepository.save(User.builder()
//                .firstName(user.getFirstName())
//                .middleName(user.getMiddleName())
//                .lastName(user.getLastName())
//                .staffId(user.getStaffId())
//                .emailAddress(user.getEmailAddress())
//                .employer(user.getEmployer())
//                .mobileNumber(user.getMobileNumber())
//                .accountNumber(user.getAccountNumber())
//                .destinationTelco(user.getDestinationTelco())
//                .salary(user.getSalary())
//                .destinationBank(user.getDestinationBank())
//                .build());

    //        return otpService.generateOtpAndSend(user.getEmailAddress());


//    public Mono<Void> generateOtp(String userId, String communicationChannel) {
//        return otpServiceImp.generateAndSendOtp(userId, communicationChannel);
//    }


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