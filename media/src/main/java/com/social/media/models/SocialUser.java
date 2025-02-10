package com.social.media.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"posts", "groups"})
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade ={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
    //@JoinColumn(name = "social_profile_id")
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonIgnore
    private Set<SocialGroup> groups = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public void setSocialProfile(SocialProfile socialProfile){
        socialProfile.setUser(this);
        this.socialProfile = socialProfile;
    }



}