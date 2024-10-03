package com.enigmacamp.barbershop.service.Impl;

import java.time.LocalTime;

import com.enigmacamp.barbershop.constant.UserRole;
import com.enigmacamp.barbershop.model.dto.request.BarberRegisterRequest;
import com.enigmacamp.barbershop.model.dto.request.LoginRequest;
import com.enigmacamp.barbershop.model.dto.request.OperationalHoursRequest;
import com.enigmacamp.barbershop.model.dto.request.RegisterRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberRegisterResponse;
import com.enigmacamp.barbershop.model.dto.response.BarberResponse;
import com.enigmacamp.barbershop.model.dto.response.LoginResponse;
import com.enigmacamp.barbershop.model.dto.response.RegisterResponse;
import com.enigmacamp.barbershop.model.entity.Role;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.UserRepository;
import com.enigmacamp.barbershop.security.JwtService;
import com.enigmacamp.barbershop.service.AuthService;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import com.enigmacamp.barbershop.model.dto.request.AdminRegisterRequest;
import com.enigmacamp.barbershop.model.dto.request.BarberRequest;
import com.enigmacamp.barbershop.model.dto.request.ServicesRequest;
import com.enigmacamp.barbershop.model.dto.request.SocialMediaRequest;
import com.enigmacamp.barbershop.model.entity.BarberProfilePicture;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.OperationalHour;
import com.enigmacamp.barbershop.model.entity.SocialMedia;
import com.enigmacamp.barbershop.service.BarbersProfilePictureService;
import com.enigmacamp.barbershop.service.CustomerService;
import com.enigmacamp.barbershop.service.OperationalHourService;
import com.enigmacamp.barbershop.service.ServiceService;
import com.enigmacamp.barbershop.service.SocialMediaService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final RoleService roleService;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final BarberService barberService;
        private final OperationalHourService operationalHourService;
        private final ServiceService serviceService;
        private final SocialMediaService socialMediaService;
        private final BarbersProfilePictureService barbersProfilePictureService;
        private final CustomerService customerService;

        @Transactional(rollbackFor = Exception.class)
        @Override
        public RegisterResponse registerCustomer(RegisterRequest request, HttpServletRequest srvrequest) {
                try {
                        Role role = roleService.getOrCreate(UserRole.valueOf(request.getRole()));

                        Users user = Users.builder()
                                        .email(request.getEmail())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .role(List.of(role))
                                        .createdAt(System.currentTimeMillis())
                                        .updateAt(System.currentTimeMillis())
                                        .build();

                        userRepository.saveAndFlush(user);

                        LoginResponse loginResponse = login(LoginRequest.builder()
                                        .email(request.getEmail()).password(request.getPassword()).build());

                        srvrequest = new HttpServletRequestWrapper(srvrequest) {
                                @Override
                                public String getHeader(String name) {
                                        if ("Authorization".equals(name)) {
                                                return "Bearer " + loginResponse.getToken();
                                        }
                                        return super.getHeader(name);
                                }
                        };

                        Customer customer = Customer.builder()
                                        .firstName(null)
                                        .surname(null)
                                        .email(request.getEmail())
                                        .about(null)
                                        .dateOfBirth(null)
                                        .userId(user)
                                        .address(null)
                                        .isMale(null)
                                        .phone(null)
                                        .address(null)
                                        .balance(0)
                                        .build();

                        customerService.create(customer, srvrequest);

                        return RegisterResponse.builder()
                                        .email(user.getUsername())
                                        .role(user.getRole().get(0).getRole().name())
                                        .build();
                } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist");
                }
        }

        @Transactional(readOnly = true)
        @Override
        public LoginResponse login(LoginRequest request) {
                try {
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        Users user = (Users) authentication.getPrincipal();
                        String token = jwtService.generateToken(user);
                        return LoginResponse.builder()
                                        .userId(user.getId())
                                        .email(user.getUsername())
                                        .role(user.getRole().get(0).getRole().name())
                                        .token(token)
                                        .build();
                } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password not correct");
                }
        }

        @Transactional(rollbackFor = Exception.class)
        @Override
        public BarberRegisterResponse registerBarber(BarberRegisterRequest request, HttpServletRequest srvrequest) {

                if (request.getBarbershop().getDescription().length() > 250) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Description cannot be more than 250 characters");
                }

                String password = request.getBarbershop().getPassword();

                if (password == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null");
                }

                try {
                        Role role = roleService.getOrCreate(UserRole.valueOf("STAFF"));

                        Users user = Users.builder()
                                        .email(request.getBarbershop().getEmail())
                                        .password(passwordEncoder.encode(request.getBarbershop().getPassword()))
                                        .role(List.of(role))
                                        .createdAt(System.currentTimeMillis())
                                        .updateAt(System.currentTimeMillis())
                                        .build();

                        userRepository.saveAndFlush(user);

                        LoginResponse loginResponse = login(LoginRequest.builder()
                                        .email(request.getBarbershop().getEmail()).password(password).build());

                        BarberRequest barbershop = request.getBarbershop();
                        BarberProfilePicture barbershop_profile_picture = barbersProfilePictureService
                                        .getByName("default.jpg");

                        barbershop.setBarbershop_profile_picture_id(barbershop_profile_picture);

                        srvrequest = new HttpServletRequestWrapper(srvrequest) {
                                @Override
                                public String getHeader(String name) {
                                        if ("Authorization".equals(name)) {
                                                return "Bearer " + loginResponse.getToken();
                                        }
                                        return super.getHeader(name);
                                }
                        };

                        BarberResponse barbers = barberService.create(srvrequest, barbershop);

                        if (request.getOperational_hours() != null) {
                                for (OperationalHoursRequest operationalHour : request.getOperational_hours()) {
                                        OperationalHour operationalHourEntity = OperationalHour.builder()
                                                        .day(operationalHour.getDay().toString())
                                                        .opening_time(LocalTime
                                                                        .parse(operationalHour.getOpening_time()))
                                                        .closing_time(LocalTime
                                                                        .parse(operationalHour.getClosing_time()))
                                                        .limitPerSession(operationalHour.getLimitPerSession() == null
                                                                        ? 1
                                                                        : operationalHour.getLimitPerSession())
                                                        .barbershop_id(barbers.toEntity())
                                                        .build();

                                        operationalHourService.create(srvrequest, operationalHourEntity);
                                }
                        }

                        if (request.getServices() != null) {

                                for (ServicesRequest service : request.getServices()) {
                                        com.enigmacamp.barbershop.model.entity.Service serviceEntity = com.enigmacamp.barbershop.model.entity.Service
                                                        .builder()
                                                        .service_name(service.getService_name())
                                                        .price(service.getPrice())
                                                        .barbershop_id(barbers.toEntity())
                                                        .build();

                                        serviceService.create(srvrequest, serviceEntity);
                                }
                        }

                        if (request.getSocial_media() != null) {

                                for (SocialMediaRequest socialMedia : request.getSocial_media()) {
                                        SocialMedia socialMediaEntity = SocialMedia.builder()
                                                        .platform_name(socialMedia.getPlatform_name())
                                                        .platform_url(socialMedia.getPlatform_url())
                                                        .barbershop_id(barbers.toEntity())
                                                        .build();

                                        socialMediaService.create(srvrequest, socialMediaEntity);

                                }
                        }

                        return BarberRegisterResponse.builder().email(user.getUsername())
                                        .role(user.getRole().get(0).getRole().name()).build();

                } catch (DataIntegrityViolationException e) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist");
                }
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public RegisterResponse registerAdmin(AdminRegisterRequest request) {

                String password = request.getPassword();
                String email = request.getEmail();

                if (password == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null");
                }

                if (email == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null");
                }

                try {
                        Role role = roleService.getOrCreate(UserRole.valueOf("ADMIN"));
                        Users user = Users.builder()
                                        .email(email)
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .role(List.of(role))
                                        .createdAt(System.currentTimeMillis())
                                        .updateAt(System.currentTimeMillis())
                                        .build();
                        userRepository.saveAndFlush(user);
                        return RegisterResponse.builder().email(user.getUsername())
                                        .role(user.getRole().get(0).getRole().name()).build();
                } catch (DataIntegrityViolationException e) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exist");
                }

        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public Users getUserByEmail(String email) {
                try {
                        return userRepository.findByEmail(email).orElse(null);
                } catch (Exception e) {
                        // TODO: handle exception
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
        }
}
