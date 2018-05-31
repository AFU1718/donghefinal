package donghe.donghestatistics.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class Entity<T> implements Serializable {

    protected Long id;
    protected Short status;
    protected Timestamp createdAt;
    protected Timestamp updatedAt;

    public abstract Long getId();

    public abstract  void setId(Long id);

    public abstract Short getStatus();

    public abstract void setStatus(Short status);

    public abstract Timestamp getCreatedAt();

    public abstract void setCreatedAt(Timestamp createdAt);

    public abstract Timestamp getUpdatedAt();
    public abstract void setUpdatedAt(Timestamp updatedAt);
}
