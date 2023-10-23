package com.drifting2.projectbackend.dao;

import com.drifting2.projectbackend.entity.Announcement;
import com.drifting2.projectbackend.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementDao {
    Page<Announcement> getAnnouncements(Integer pageSize, Integer page);

    Page<Announcement> getAnnouncements(String name, Pageable page);
    Announcement getAnnouncements(String announcementId);
    Announcement save(Announcement announcement);
}
