package com.drifting2.projectbackend.service;

import com.drifting2.projectbackend.dao.AnnouncementDao;
import com.drifting2.projectbackend.entity.Announcement;
import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.entity.Teacher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService{
    final AnnouncementDao announcementDao;

    @Override
    public Page<Announcement> getAnnouncement(Integer pageSize, Integer page) {
        return announcementDao.getAnnouncements(pageSize, page);
    }

    @Override
    public Page<Announcement> getAnnouncement(String title, Pageable pageable) {
        return announcementDao.getAnnouncements(title,pageable);
    }

    @Override
    public Announcement getAnnouncement(String announcementId) {
        return announcementDao.getAnnouncements(announcementId);
    }

    @Override
    @Transactional
    public Announcement save(Announcement announcement) {
        return announcementDao.save(announcement);
    }
}
