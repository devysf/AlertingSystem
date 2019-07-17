package com.yte.springreact.alertingsystem.service.auth;


import com.yte.springreact.alertingsystem.entity.auth.Role;
import com.yte.springreact.alertingsystem.entity.auth.User;
import com.yte.springreact.alertingsystem.repository.auth.RoleRepository;
import com.yte.springreact.alertingsystem.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        System.out.println("Save user" + user.getId());
        //Control get id because of updating
        if(user.getId() == null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            //add normal user role to new user
            Role role = new Role();
            role.setId(1L);
            role.setName("NORMAL");
            roleRepository.save(role);

            user.getRoles().add(role);
        }

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
