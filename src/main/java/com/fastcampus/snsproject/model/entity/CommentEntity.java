package com.fastcampus.snsproject.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"comment\"", indexes = {
        @Index(name = "post_id_idx" ,columnList = "post_id")
})
@Getter
@Setter
@SQLDelete(sql = "UPDATE \"comment\" SET deleted_at = NOW() where id = ?")
@Where(clause = "deleted_at is NULL")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "comment")
    private String comment;

    @Column(name = "register_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;


    @PrePersist
    void registedAt(){
        this.registerAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updateAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static CommentEntity of(UserEntity userEntity, PostEntity postEntity, String comment){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUser(userEntity);
        commentEntity.setPost(postEntity);
        commentEntity.setComment(comment);

        return commentEntity;
    }
}
