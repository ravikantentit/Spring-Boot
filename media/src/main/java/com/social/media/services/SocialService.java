package com.social.media.services;

import com.social.media.models.SocialUser;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;




@Service
public class SocialService {
    @Autowired
    SocialUserRepository socialUserRepository;

    @Autowired
    PostRepository postRepository;

    public List<SocialUser> getAllUsers() {

        return socialUserRepository.findAll();
    }

    public SocialUser saveUsers(SocialUser socialUser) {

        if(socialUser.getId() != null) {
            SocialUser existingUser = socialUserRepository.findById(socialUser.getId()).orElseThrow(
                    () -> new RuntimeException("User Already exist"));
            existingUser.setId(socialUser.getId());
            existingUser.setSocialProfile(socialUser.getSocialProfile());
            existingUser.setGroups(socialUser.getGroups());
            return socialUserRepository.save(existingUser);
        }
       else
        return socialUserRepository.save(socialUser);
    }

    @Transactional
    public SocialUser deleteUsers(Long userId) {

        SocialUser socialUser1;
        if(userId == null) {
            throw new RuntimeException("User not found");
        } else {
            List<SocialUser> socialUser = getAllUsers();

            socialUser1 = socialUser.stream()
                    .filter(user -> userId.equals(user.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));
            socialUserRepository.delete(socialUser1);
        }
        return socialUser1;
    }

}
