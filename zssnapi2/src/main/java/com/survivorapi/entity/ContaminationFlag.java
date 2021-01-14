/**
 * 
 */
package com.survivorapi.entity;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Beatriz Almeida Sobrinho em 11/01/21
 * A ContaminationFlag.
 */
@Entity
@Table(name = "contamination_flag")
public class ContaminationFlag implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@NotNull
	private Survivor reportedBy;

	@ManyToOne(optional = false)
	@NotNull
	private Survivor reported;

	@Deprecated
	public ContaminationFlag() {
	}

	public ContaminationFlag(Survivor reported, Survivor survivorReporter) {
		this.reported = reported;
		this.reportedBy = survivorReporter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Survivor getReportedBy() {
		return reportedBy;
	}

	public ContaminationFlag reportedBy(Survivor survivor) {
		this.reportedBy = survivor;
		return this;
	}

	public void setReportedBy(Survivor survivor) {
		this.reportedBy = survivor;
	}

	public Survivor getReported() {
		return reported;
	}

	public ContaminationFlag reported(Survivor survivor) {
		this.reported = survivor;
		return this;
	}

	public void setReported(Survivor survivor) {
		this.reported = survivor;
	}
	

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContaminationFlag contaminationFlag = (ContaminationFlag) o;
		if (contaminationFlag.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), contaminationFlag.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ContaminationFlag{" + "id=" + getId() + "}";
	}

}
