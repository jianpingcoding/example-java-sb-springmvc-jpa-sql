package org.ganjp.example.bm.Service;

import org.ganjp.example.bm.entity.Profile;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.bm.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    @Transactional(readOnly=true)
    public Profile fetchProfileWithoutAvatar(String id) {
        return profileRepository.findById(id).get();
    }

    @Transactional(readOnly=true)
    public Profile fetchProfileWithAvatar(String id) {
        Optional<Profile> profile = profileRepository.findById(id);

        // this is loaded LAZY
        profile.get().getAvatar();

        return profile.get();
    }

    @Autowired
    private ProfileRepository profileRepository;
}
