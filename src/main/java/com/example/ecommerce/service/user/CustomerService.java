package com.example.ecommerce.service.user;

import com.example.ecommerce.builder.user.CustomerBuilder;
import com.example.ecommerce.config.app.RegexValidation;
import com.example.ecommerce.dto.user.CustomerRequestDto;
import com.example.ecommerce.entity.user.Customer;
import com.example.ecommerce.entity.user.RegisterType;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.entity.user.auth.Otp;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.InvalidFormatException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.exception.ResourceAlreadyExistException;
import com.example.ecommerce.repository.user.CustomerRepository;
import com.example.ecommerce.service.message.email.EmailProviderFactory;
import com.example.ecommerce.service.message.sms.SmsProviderFactory;
import com.example.ecommerce.service.user.auth.OtpService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CustomerBuilder customerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, CustomerBuilder customerBuilder, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.customerBuilder = customerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    public String createCustomerWithEmailPassword(CustomerRequestDto customerRequestDto) {
        if (!RegexValidation.isValidEmail(customerRequestDto.getUsername()))
            throw new InvalidFormatException("Invalid email address");

        if (!RegexValidation.isValidPasswword(customerRequestDto.getPassword()))
            throw new InvalidFormatException("Invalid password");

        if (userService.isExistUserByUsername(customerRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("Username already exists");

        Customer customer = customerBuilder.customerRequestDtoToCustomer(customerRequestDto, RegisterType.EMAIL);
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));
        Customer save = customerRepository.save(customer);

        if (sendOtpEmail(save).equals("success email")){
            return "success email";
        }else
            throw new BadRequestException("Sms Not Send");

    }

    public String createCustomerWithPhoneNumber(CustomerRequestDto customerRequestDto) {
        if (!RegexValidation.isValidPhoneNumber(customerRequestDto.getUsername()))
            throw new InvalidFormatException("Invalid phone number");

        if (!RegexValidation.isValidPasswword(customerRequestDto.getPassword()))
            throw new InvalidFormatException("Invalid password");

        if (userService.isExistUserByPhone(customerRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("Username already exists");

        Customer customer = customerBuilder.customerRequestDtoToCustomer(customerRequestDto, RegisterType.PHONE);
        customer.setUsername(null);
        customer.setPhoneNo(customerRequestDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));
        Customer save = customerRepository.save(customer);

        if (sendOtpSms(save).equals("success sms")){
            return "success sms";
        }else
            throw new BadRequestException("Sms Not Send");

    }

    public Customer createCustomerWithGoogle(String name,String username){
        if (!RegexValidation.isValidEmail(username))
            throw new InvalidFormatException("Invalid email address");

        if (userService.isExistUserByUsername(username))
            throw new ResourceAlreadyExistException("Username already exists");

        Customer customer = new Customer(name,username,RegisterType.GOOGLE);
         return customerRepository.save(customer);

    }


    public String verifyOtp(String username, String otpNo) {
        Customer customer;
        if (RegexValidation.isValidEmail(username)){
            customer = findByUsername(username);
        } else if (RegexValidation.isValidPhoneNumber(username)) {
            customer = findByPhoneNo(username);
        }else
            throw new InvalidFormatException("Invalid username");

        if (!customer.isEnabled()){
            Otp otp = otpService.findTopByUserOrderByCreatedAtDesc(customer);
            if (otp.isValid() && otp.getExpired().isAfter(LocalDateTime.now())){
                if (otp.getOtp().equals(otpNo)){
                    customer.setEnabled(true);
                    customer.setAccountNonLocked(true);
                    customerRepository.save(customer);
                    otpService.falseOtp(otp);
                }else
                    throw new BadRequestException("Bad Code");
            }else
                throw new BadRequestException("Otp is Not Valid");
        }else
            throw new BadRequestException("Otp is Enabled");

        return "Success verify Otp";
    }


    public String sendOtp(String username) {
        Customer customer;
        if (RegexValidation.isValidEmail(username)){
            System.out.println("email");
            customer = findByUsername(username);

            if (customer.isEnabled())
                throw new BadRequestException("User already enabled");

            // eğer daha önceden otp gönderildiyse onun değerlerini null değere dönüştürür
            Otp otp = otpService.findTopByUserOrderByCreatedAtDescOrNull(customer);
            if (otp != null)
                otpService.falseOtp(otp);

            return sendOtpEmail(customer);
        } else if (RegexValidation.isValidPhoneNumber(username)) {
            System.out.println("phone number");
            customer = findByPhoneNo(username);
            if (customer.isEnabled())
                throw new BadRequestException("User already enabled");

            // eğer daha önceden otp gönderildiyse onun değerlerini null değere dönüştürür
            Otp otp = otpService.findTopByUserOrderByCreatedAtDescOrNull(customer);
            if (otp != null)
                otpService.falseOtp(otp);

            return sendOtpSms(customer);
        }else
            throw new InvalidFormatException("Invalid username");



    }


    private String sendOtpEmail(Customer customer) {
        if (isOtpLimit(customer))
            throw new InvalidFormatException("Otp limit exceeded");

        Random random = new Random();
        int otpNo = random.nextInt(89999)+10000;
        Otp otp = otpService.createOtp(customer, String.valueOf(otpNo));

        Map<String, String> config = new HashMap<>();
        config.put("apiKey", "your-api-key");
        config.put("apiSecret", "your-api-secret");
        config.put("fromEmail", "yur-email");

        return EmailProviderFactory
                .getProvider("GMAIL",config)
                .sendEmail(customer.getUsername(),"subject",otp.getOtp());
    }


    private String sendOtpSms(Customer customer) {
        if (isOtpLimit(customer))
            throw new InvalidFormatException("Otp limit exceeded");

        Random random = new Random();
        int otpNo = random.nextInt(89999)+10000;
        Otp otp = otpService.createOtp(customer, String.valueOf(otpNo));

        Map<String, String> config = new HashMap<>();
        config.put("apiKey", "your-api-key");
        config.put("apiSecret", "your-api-secret");
        config.put("fromNumber", "+1234567890");
        return SmsProviderFactory
                .getProvider("TWILIO",config)
                .sendSms(customer.getPhoneNo(), otp.getOtp());
    }

    public boolean isOtpLimit(Customer customer) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);

        List<Otp> allOtpToday = otpService.findAllByUserAndCreatedToday(customer, startOfDay, endOfDay);
        return allOtpToday.size() > 2;
    }


    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Customer not found"));
    }
     public Optional<Customer> findByUsernameOrNull(String username) {
        return customerRepository.findByUsername(username);
    }


    public Customer findByPhoneNo(String phoneNo) {
        return customerRepository.findByPhoneNo(phoneNo).orElseThrow(()-> new NotFoundException("Customer not found"));
    }


    public Customer findUser(String username) {
        if (RegexValidation.isValidEmail(username)){
            return findByUsername(username);
        } else if (RegexValidation.isValidPhoneNumber(username)) {
            return findByPhoneNo(username);
        }else
            throw new InvalidFormatException("Invalid email address");
    }

    public String deleteCustomer(String username) {
        Customer customer = findUser(username);
        customer.setAccountNonLocked(false);
        customerRepository.save(customer);
        return "Customer is inActive";
    }

    public Optional<Customer> findCustomer(String username) {
        Optional<User> user = userService.getUserOrNull(username); // eğer kullanıcı mevcutsa kullanıcıyı çeker
        if (user.isPresent() && user.get() instanceof Customer) { // Customer olup olmadığını kontrol eder
            return findByUsernameOrNull(username); // Customer ise kullanıcıyı döner
        } else {
            throw new InvalidFormatException("Invalid user"); // Aksi halde hata fırlatır
        }
    }


    public boolean isExistUser(String username) {
        return userService.isExistUserByUsername(username);
    }


    public boolean isExistCustomer(String email) {
        return customerRepository.existsByUsername(email);
    }
}
