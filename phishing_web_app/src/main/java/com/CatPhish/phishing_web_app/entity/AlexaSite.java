package com.CatPhish.phishing_web_app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "top_sites")
@Data
public class AlexaSite {

    @Id
    private String id;  // MongoDB will automatically create the _id field if not defined

    @Field("Domain_Name") // MongoDB field name is "Domain_Name"
    private String domainName;

    @Field("Rank") // MongoDB field name is "Rank"
    private int rank;

    // Getter and Setter for domainName

}
