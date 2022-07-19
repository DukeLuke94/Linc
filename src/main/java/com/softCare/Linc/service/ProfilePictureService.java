package com.softCare.Linc.service;

import com.softCare.Linc.model.ProfilePicture;
import com.softCare.Linc.repository.ProfilePictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilePictureService implements ProfilePictureServiceInterface {

    private final ProfilePictureRepository profilePictureRepository;

    public ProfilePictureService(ProfilePictureRepository profilePictureRepository) {
        this.profilePictureRepository = profilePictureRepository;
    }

    public ProfilePicture store(ProfilePicture profilePicture) {
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        ProfilePicture profilePicture = new ProfilePicture(fileName, file.getContentType(), file.getBytes());
        return profilePictureRepository.save(profilePicture);
    }

    public Optional<ProfilePicture> getProfilePictureByID(Long pictureId) {
        return profilePictureRepository.findById(pictureId);
    }

    public List<ProfilePicture> getAllFiles() {
        return profilePictureRepository.findAll();
    }
}
