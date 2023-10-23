package com.drifting2.projectbackend.service;

import com.drifting2.projectbackend.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementService {

    Page<Announcement> getAnnouncement(Integer pageSize, Integer page);

    Page<Announcement> getAnnouncement(String title, Pageable pageable);

    Announcement getAnnouncement(String announcementId);
    Announcement save(Announcement announcement);
}
