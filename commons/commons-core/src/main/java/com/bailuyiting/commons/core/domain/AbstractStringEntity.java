package com.bailuyiting.commons.core.domain;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类的基类
 * 
 * @author  
 * 
 */
@MappedSuperclass
public abstract class AbstractStringEntity extends AbstractSuperEntity<String> implements Serializable {

  private static final long serialVersionUID = -8731260486753288680L;

  @Id
  @GeneratedValue(generator = "UUIDGen")
  @GenericGenerator(name = "UUIDGen", strategy = "uuid")
  @Column(name = "ID", length = 32, updatable = false, nullable = false)
  private String id = null;

  /*
   * (non-Javadoc)
   * 
   * @see  
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the entity.
   * 
   * @param id the id to set
   */
  public void setId(final String id) {

    this.id = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see  
   */
  public boolean isNew() {

    return null == getId();
  }

  /*
   * transfor the @see java.util.Date to @see org.joda.time.DateTime
   */
  public DateTime toDateTime(final Date date) {

    return null == date ? null : new DateTime(date);
  }

  /*
   * transfor the @see org.joda.time.DateTime to @see java.util.Date
   */
  public Date toDate(final DateTime datetime) {

    return null == datetime ? null : datetime.toDate();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AbstractStringEntity)) {
      return false;
    }
    final AbstractStringEntity that = (AbstractStringEntity) obj;

    return null == this.getId() ? false : this.getId().equals(that.getId());

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashCode = 17;

    hashCode += null == getId() ? 0 : getId().hashCode() * 31;

    return hashCode;
  }

  protected void copy(final AbstractStringEntity source) {
    this.id = source.id;
  }
  
}
