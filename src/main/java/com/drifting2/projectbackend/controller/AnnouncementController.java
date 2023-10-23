package com.drifting2.projectbackend.controller;

import com.drifting2.projectbackend.entity.Announcement;
import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.service.AnnouncementService;
import com.drifting2.projectbackend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class AnnouncementController {

    final AnnouncementService announcementService;
    @GetMapping("/announcements")
    public ResponseEntity<?> getAnnouncementList(@RequestParam(value ="_limit", required = false) Integer perPage,
                                             @RequestParam(value = "_page", required = false) Integer page,
                                             @RequestParam(value = "title", required = false) String title) {

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Announcement> pageOutput;
        if (title == null) {
            pageOutput = announcementService.getAnnouncement(perPage,page);
        }else{
            pageOutput = announcementService.getAnnouncement(title, PageRequest.of(page-1,perPage));
        }
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getAnnouncementDTO(pageOutput.getContent()),responseHeader, HttpStatus.OK);

    }

    @GetMapping("/announcements/{id}")
    public ResponseEntity<?> getAnnouncement(@RequestParam String id) {
        Announcement output = announcementService.getAnnouncement(id);
        if (output != null){
            return ResponseEntity.ok(LabMapper.INSTANCE.getAnnouncementDTO(output));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given id is not found");
        }
    }

    @PostMapping("/saveannouncements")
    public ResponseEntity<?> addAnnouncement(@RequestBody Announcement announcement){
        Announcement output = announcementService.save(announcement);
        return ResponseEntity.ok(LabMapper.INSTANCE.getAnnouncementDTO(output));
    }
}
