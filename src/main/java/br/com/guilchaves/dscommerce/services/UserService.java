package br.com.guilchaves.dscommerce.services;

import br.com.guilchaves.dscommerce.dto.UserDTO;
import br.com.guilchaves.dscommerce.entities.Role;
import br.com.guilchaves.dscommerce.entities.User;
import br.com.guilchaves.dscommerce.projections.UserDetailsProjection;
import br.com.guilchaves.dscommerce.repository.UserRepository;
import br.com.guilchaves.dscommerce.utils.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomUserUtil customUserUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    protected User authenticated() {
        try {
            String username = customUserUtil.getLoggedUsername();
            return repository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }

}
