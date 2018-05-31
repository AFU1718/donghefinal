package donghe.donghestatistics.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity<T extends BaseEntity> extends Entity<T> {
    @Id
    @GeneratedValue
    @Column(length = 20)
    protected Long id;
    protected Short status;
    protected Timestamp createdAt;
    protected Timestamp updatedAt;

    public BaseEntity() {
        this.status=1;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());

    }

    public BaseEntity(Long id,Short status, Timestamp createdAt, Timestamp updatedAt) {
        this.id=id;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Short getStatus() {
        return status;
    }

    @Override
    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

