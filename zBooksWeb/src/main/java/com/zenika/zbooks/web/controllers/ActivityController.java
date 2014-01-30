package com.zenika.zbooks.web.controllers;

import com.zenika.zbooks.entity.Activity;
import com.zenika.zbooks.exceptions.InvalidResourceException;
import com.zenika.zbooks.persistence.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping(value = "/api/activities")
@Controller
public class ActivityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityMapper activityMapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Activity> list(UriComponentsBuilder uriBuilder, @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "") String sortBy,  @RequestParam(defaultValue = "5") int nbResults,
                      @RequestParam(defaultValue = "ASC") String order) {
        sortBy = sortBy.equalsIgnoreCase("ASC")?"ASC":"DESC";
        order = sortBy.equalsIgnoreCase("DESC")?"ASC":"DESC";

        List<Activity> activities = activityMapper.getActivities();

        return activities;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Activity getActivity(@PathVariable int id) {
        return activityMapper.getActivity(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createActivity(@RequestBody Activity activity, UriComponentsBuilder builder) {
        if (activity == null) {
            throw new InvalidResourceException(); // TODO add error description
        }

        activityMapper.addActivity(activity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/activities/{id}").buildAndExpand(activity.getId()).toUri());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

}
