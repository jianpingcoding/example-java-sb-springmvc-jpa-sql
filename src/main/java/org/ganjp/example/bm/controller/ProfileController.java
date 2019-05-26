package org.ganjp.example.bm.controller;

import org.ganjp.example.bm.Service.ProfileService;
import org.ganjp.example.bm.entity.Profile;
import org.ganjp.example.common.controller.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @GetMapping("/{userId}")
    public SuccessResponse fetchUser(@PathVariable String userId) {
        Profile profile = profileService.fetchProfileWithAvatar(userId);
//        profile.setAvatar(new byte[0]);
        return new SuccessResponse("Get Profile successfully","profile", profile);
    }

    @Autowired
    ProfileService profileService;
}
