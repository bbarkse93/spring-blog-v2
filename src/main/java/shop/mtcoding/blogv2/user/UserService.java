package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyPath;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

// Service의 책임
// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + joinDTO.getPic().getOriginalFilename();
        System.out.println("fileName: " + fileName);

        // 프로젝트를 실행파일로 변경 => blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(fileName)
                .build();
        userRepository.save(user); // em.persist
    }

    public void 중복체크(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new MyApiException("이미 사용 중인 유저네임 입니다.");
        }

    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        // 1. 유저네임 검증
        if (user == null) {
            throw new MyException("유저네임이 없습니다.");
        }
        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 잘못되었습니다");
        }
        // 3. 로그인 성공
        return user;
    }

    // // 다른 방법
    // public User 로그인(LoginDTO loginDTO) {
    // User user = userRepository.findByUsername(loginDTO.getUsername());
    // if (user == null) { // username이 존재하지 않음.
    // return null;
    // } else { // username이 존재함.
    // if (user.getPassword().equals(loginDTO.getPassword())) {
    // return user;
    // } else {
    // return null;
    // }
    // }
    // }

    public User 회원정보보기(Integer id) {

        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UpdateDTO updateDTO, Integer id) {

        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid + "_" + updateDTO.getPic().getOriginalFilename();
        System.out.println("fileName : " + fileName);

        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, updateDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());
        user.setPicUrl(fileName);

        return user;
    } // 3. Flush

}
