package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.saveDTO saveDTO) {
        User user = User.builder()
                .username(saveDTO.getUsername())
                .password(saveDTO.getPassword())
                .email(saveDTO.getEmail())
                .build();
        userRepository.save(user);
    }

    public void 로그인(UserRequest.loginDTO loginDTO) {
        userRepository.findByUsername(loginDTO.getUsername());
    }
}
