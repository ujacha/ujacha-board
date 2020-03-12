package net.ujacha.board.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class CommonEntity {
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private LocalDateTime deletedAt;

    public static void initEntity(CommonEntity entity) {
        final LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setLastModifiedAt(now);
        entity.setDeletedAt(null);
    }

}
