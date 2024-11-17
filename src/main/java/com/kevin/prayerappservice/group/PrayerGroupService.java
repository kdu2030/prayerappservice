package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.FileRepository;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import com.kevin.prayerappservice.utils.ColorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        int colorInt = ColorUtils.colorHexStringToInt(newPrayerGroup.getColor());
        PrayerGroup prayerGroup = new PrayerGroup(newPrayerGroup.getName(), newPrayerGroup.getDescription(),
                newPrayerGroup.getRules(), colorInt, imageFile.orElse(null));

    }


}
