package com.example.ecommerce.service.user.auth;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.entity.user.auth.Otp;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.user.auth.OtpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OtpService {
    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public Otp createOtp(User user,String otpNo) {
        Otp otp = new Otp(otpNo, user);
        return otpRepository.save(otp);
    }

    public Otp findByOtpNo(String otpNo) {
        return otpRepository.findByOtp(otpNo).orElseThrow(()-> new NotFoundException("Otp Not Found"));
    }

    public Otp findTopByUserOrderByCreatedAtDesc(User user) {
        return otpRepository.findTopByUserOrderByCreatedAtDesc(user.getId()).orElseThrow(()-> new NotFoundException("Otp Not Found"));
    }
    public Otp findTopByUserOrderByCreatedAtDescOrNull(User user) {
        return otpRepository.findTopByUserOrderByCreatedAtDesc(user.getId()).get();
    }
    public List<Otp> findAllByUser(User user) {
        return otpRepository.findAllByUser(user);
    }

    public Otp findById(int id){
        return otpRepository.findById(id).orElseThrow(()-> new NotFoundException("Otp Not Found"));
    }

    public String deleteOtpById(int id) {
        otpRepository.deleteById(id);
        if (!otpRepository.existsById(id)) {
            return "success";
        }else
            throw new BadRequestException("Otp Not Deleted");
    }


    public void falseOtp(Otp otp) {
        otp.setValid(false);
        otpRepository.save(otp);
    }

    public List<Otp> findAllByUserAndCreatedToday(User user, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return otpRepository.findAllByUserAndCreatedToday(user, startOfDay, endOfDay);
    }
}
