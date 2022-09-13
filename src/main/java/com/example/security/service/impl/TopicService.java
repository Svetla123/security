package com.example.security.service.impl;

import com.example.security.model.Topic;
import com.example.security.repository.TopicRepository;
import com.example.security.service.ITopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService implements ITopicService {

    private TopicRepository topics;

    private TopicService(TopicRepository topics) {
        super();
        this.topics = topics;
    }
    @Override
    public Topic findTopicById(Long id) {
        try{
            return this.topics.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Topic> findAllTopics() {
        return this.topics.findAll();
    }

    @Override
    public Topic createTopic(Topic topic) {
        return this.topics.save(topic);
    }

    @Override
    public boolean deleteTopic(long id){
        Topic t = this.findTopicById(id);
        try {
            this.topics.delete(t);
        }
        catch (Exception e) {
            return false;
        }finally {
            return true;
        }
    }

    @Override
    public Topic updateTopic (long id, Topic topic) {
        Topic oldTopic = this.findTopicById(id);

        if (topic == null) {
            return null;
        }else {
            if (topic.getName() != null){
                oldTopic.setName(topic.getName());
            }
            if (topic.getDescription() != null){
                oldTopic.setDescription(topic.getDescription());
            }
            if (topic.getExpectedHours() != null) {
                oldTopic.setExpectedHours(topic.getExpectedHours());
            }
        }
        return this.topics.save(oldTopic);
    }

    @Override
    public Long findTopicByName(String name) {
        return this.topics.findByName(name).getId();
    }
}
