package com.example.security.service;


import com.example.security.model.Topic;

import java.util.List;

public interface ITopicService {
    Topic findTopicById(Long id);
    List<Topic> findAllTopics();

    Topic createTopic(Topic topic);

    boolean deleteTopic(long id);

    Topic updateTopic (long id, Topic topic);

    Long findTopicByName(String name);
}
