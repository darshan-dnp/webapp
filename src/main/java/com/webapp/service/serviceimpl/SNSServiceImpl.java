package com.webapp.service.serviceimpl;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.webapp.service.SNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class SNSServiceImpl implements SNSService {
    @Autowired
    private AmazonSNS sns;

    @Value("${topic.arn}")
    private String topicARN;

    public void pubTopic(String message) {
        System.out.println(topicARN);
        try {
            PublishRequest request = new PublishRequest(topicARN, message);
            sns.publish(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
