package com.drifting2.projectbackend.dao;

import com.drifting2.projectbackend.entity.Announcement;
import com.drifting2.projectbackend.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@Profile("db")
public class AnnouncementDaoDbImpl implements AnnouncementDao{
    final AnnouncementRepository announcementRepository;

    @Override
    public Page<Announcement> getAnnouncements(Integer pageSize, Integer page) {
        return announcementRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Page<Announcement> getAnnouncements(String name, Pageable page) {
        return announcementRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContaining(name, name, page);
    }

    @Override
    public Announcement getAnnouncements(String announcementId) {
        return announcementRepository.findByAnnouncementId(announcementId);
    }

    @Override
    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
}
