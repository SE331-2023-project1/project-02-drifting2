package com.drifting2.projectbackend.repository;

import com.drifting2.projectbackend.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Announcement findByAnnouncementId(String announcementId);

    Page<Announcement> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContaining(String title, String content, Pageable pageRequest);
}
