package com.example.TestParserExecutor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "buildResults")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long buildNum;

    private int testCount;

    private int passedCount;

    private int skippedCount;

    private int failedCount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @ToString.Exclude
    private List<Test> tests;

    @Column(length = 10485760)
    private String consoleOut;
}
