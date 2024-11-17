package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.FileRepository;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class PrayerGroupService {
    private final PrayerGroupRepository prayerGroupRepository;
    private final FileRepository fileRepository;
    private final JwtService jwtService;

    @Autowired
    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository, JwtService jwtService, FileRepository fileRepository) {
        this.prayerGroupRepository = prayerGroupRepository;
        this.jwtService = jwtService;
        this.fileRepository = fileRepository;
    }

    public void createPrayerGroup(String authorization, NewPrayerGroup newPrayerGroup) {
        if(prayerGroupRepository.findByGroupName(newPrayerGroup.getName()).isPresent()){
            throw new DataValidationException(new String[] {"A group with this name already exists."});
        }
        Optional<File> imageFile = fileRepository.findById(newPrayerGroup.getImageFileId());
        PrayerGroup prayerGroup = new PrayerGroup(newPrayerGroup.getName(), newPrayerGroup.getDescription(),
                newPrayerGroup.getRules(), newPrayerGroup.getColor(), imageFile.orElse(null));
    }


}
