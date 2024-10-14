package org.willbemsa.willbelogging.shared.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorRepository;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserEntity;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserRepository;
import org.willbemsa.willbelogging.shared.user.model.CustomUserDetails;

import java.util.Optional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {




    private final GoogleUserRepository googleUserRepository;
    private final BehaviorRepository behaviorRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        GoogleUserEntity user = googleUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user); // CustomUserDetails 사용
    }

    public GoogleUserEntity getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }

    public BehaviorEntity userHasBehavior(
            GoogleUserEntity googleUserEntity, Long behaviorId
    ){
        Optional<BehaviorEntity> optBehaviorEntity= behaviorRepository.findById(behaviorId);
        if(optBehaviorEntity.isPresent()){

            BehaviorEntity behavior = optBehaviorEntity.get();
            if(behavior.getStudentEntity().getGoogleUser().getId().equals(googleUserEntity.getId())){

                log.info("행동의 유저 아이디"+behavior.getStudentEntity().getGoogleUser().getId());

                log.info(" 유저 아이디"+googleUserEntity.getId());
                return behavior;

            }
        }

        return null;
    }



}
