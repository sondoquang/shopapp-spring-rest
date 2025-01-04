package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtils;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        // REGISTER USER //
        String phoneNumber = userDTO.getPhoneNumber();
        // 1. check valid user by phone number //
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already exists");
        }
        // 2. Convert form UserDTo to User //
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(phoneNumber)
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        newUser.setRole(role);

        // 3. Kiểm nếu có accountId --> không cần password
        if(userDTO.getFacebookAccountId() == 0 || userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
            // Mã hóa code //
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if(userOptional.isEmpty()){
            throw new DataNotFoundException("Wrong phone number or password");
        }
        // get User //
        User existingUser = userOptional.get();

        // Check password //
        if(existingUser.getFacebookAccountId() == 0 || existingUser.getGoogleAccountId() == 0){
            if(passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            };
        }

        // Authenticate with Java Spring Security //
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationToken(
                phoneNumber,
                password
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationFilter);
        return jwtTokenUtils.generateToken(existingUser);// Muốn trả về JWT token ?
    }
}
