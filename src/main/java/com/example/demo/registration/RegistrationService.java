package com.example.demo.registration;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;
import com.example.demo.appuser.AppUserService;
import com.example.demo.email.EmailSender;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link)
        );
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "            </td>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "              <h1 style=\"color:#ffffff;font-weight:bold;vertical-align:middle\">Confirm your email</h1>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px;margin:0 auto\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"30\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"100%\" style=\"padding:20px 30px 40px 30px\">\n" +
                "        <p style=\"margin:0 0 20px 0;font-size:19px\">Hi " + name + ",</p>\n" +
                "        <p style=\"margin:0 0 20px 0;font-size:19px\">Thank you for registering. Please click on the below link to activate your account:</p>\n" +
                "        <p style=\"margin:0 0 20px 0;font-size:19px\"><a href=\"" + link + "\" style=\"color:#1D70B8;text-decoration:none\">Activate Now</a></p>\n" +
                "        <p style=\"margin:0 0 20px 0;font-size:19px\">Link will expire in 15 minutes.</p>\n" +
                "        <p style=\"margin:0 0 20px 0;font-size:19px\">See you soon!</p>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"100%\" height=\"30\" bgcolor=\"#f0f0f0\">\n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"100%\" style=\"padding:15px;font-family:sans-serif;font-size:12px;line-height:18px;color:#999999\">\n" +
                "              <p style=\"margin:0\">If you didn't request this email, you can safely ignore it.</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "</div>";
    }
}