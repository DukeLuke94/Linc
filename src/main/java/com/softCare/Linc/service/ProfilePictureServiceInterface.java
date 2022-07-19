package com.softCare.Linc.service;

import com.softCare.Linc.model.ProfilePicture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProfilePictureServiceInterface {

    ProfilePicture store(ProfilePicture profilePicture);

    Optional<ProfilePicture> getProfilePictureByID(Long pictureId);

    List<ProfilePicture> getAllFiles();

}
