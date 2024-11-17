package com.kevin.prayerappservice.group;

import com.kevin.prayerappservice.auth.JwtService;
import com.kevin.prayerappservice.exceptions.DataValidationException;
import com.kevin.prayerappservice.file.FileRepository;
import com.kevin.prayerappservice.file.entities.File;
import com.kevin.prayerappservice.group.entities.PrayerGroup;
import com.kevin.prayerappservice.group.entities.PrayerGroupRole;
import com.kevin.prayerappservice.group.entities.PrayerGroupUser;
import com.kevin.prayerappservice.group.models.NewPrayerGroup;
import com.kevin.prayerappservice.group.models.PrayerGroupSummary;
import com.kevin.prayerappservice.user.UserRepository;
import com.kevin.prayerappservice.user.entities.User;
import com.kevin.prayerappservice.utils.ColorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrayerGroupService {
    private final PrayerGroupRepository prayerGroupRepository;
    private final FileRepository fileRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PrayerGroupUserRepository prayerGroupUserRepository;


    @Autowired
    public PrayerGroupService(PrayerGroupRepository prayerGroupRepository, JwtService jwtService,
                              FileRepository fileRepository, UserRepository userRepository,
                              PrayerGroupUserRepository prayerGroupUserRepository) {
        this.prayerGroupRepository = prayerGroupRepository;
        this.jwtService = jwtService;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.prayerGroupUserRepository = prayerGroupUserRepository;
    }

    public PrayerGroupSummary createPrayerGroup(String authorization, NewPrayerGroup newPrayerGroup) {
        if (prayerGroupRepository.findByGroupName(newPrayerGroup.getName()).isPresent()) {
            throw new DataValidationException(new String[]{"A group with this name already exists."});
        }

        Optional<File> imageFile = fileRepository.findById(newPrayerGroup.getImageFileId());
        int colorInt = ColorUtils.colorHexStringToInt(newPrayerGroup.getColor());
        PrayerGroup prayerGroup = new PrayerGroup(newPrayerGroup.getName(), newPrayerGroup.getDescription(),
                newPrayerGroup.getRules(), colorInt, imageFile.orElse(null));

        prayerGroupRepository.save(prayerGroup);

        String token = jwtService.extractTokenFromAuthHeader(authorization);
        String username = jwtService.extractUsername(token);

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            PrayerGroupUser prayerGroupUser = new PrayerGroupUser(PrayerGroupRole.ADMIN, user.get(), prayerGroup);
            prayerGroupUserRepository.save(prayerGroupUser);
        }

        return PrayerGroupSummary.createPrayerGroupSummary(prayerGroup);
    }


}
