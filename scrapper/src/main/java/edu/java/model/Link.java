package edu.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "link")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url")
    private String url;

    @Column(name = "updated_at")
    private OffsetDateTime updateAt;

    @Column(name = "checked_at")
    private OffsetDateTime checkAt;

    @ManyToMany(mappedBy = "links")
    private List<Chat> chats;

    public Link(String url) {
        this.id = Long.MIN_VALUE;
        this.url = url;
        this.updateAt = OffsetDateTime.now(ZoneId.systemDefault());
        this.checkAt = OffsetDateTime.now(ZoneId.systemDefault());
    }

    public Link(long id, String url, OffsetDateTime updateAt, OffsetDateTime checkAt) {
        this.id = id;
        this.url = url;
        this.updateAt = updateAt;
        this.checkAt = checkAt;
    }
}
