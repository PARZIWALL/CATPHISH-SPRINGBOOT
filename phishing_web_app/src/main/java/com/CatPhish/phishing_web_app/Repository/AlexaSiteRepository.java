package com.CatPhish.phishing_web_app.Repository;

import com.CatPhish.phishing_web_app.entity.AlexaSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlexaSiteRepository extends MongoRepository<AlexaSite, String> {
    AlexaSite findByDomainName(String domainName); // Query by domain name
}
