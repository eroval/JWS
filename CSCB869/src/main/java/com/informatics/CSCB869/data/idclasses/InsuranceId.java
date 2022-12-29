package com.informatics.CSCB869.data.idclasses;

import java.io.Serializable;
import java.time.LocalDate;

import com.informatics.CSCB869.data.entity.Insurance;


public class InsuranceId implements Serializable{
    private LocalDate date;
    private long patientId;

    public InsuranceId (){
    }

    public InsuranceId (LocalDate date, long patient_id){
        this.setDate(date);
        this.setPatientId(patient_id);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getPatientId() {
        return patientId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + (int) (patientId ^ (patientId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InsuranceId other = (InsuranceId) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (patientId != other.patientId)
            return false;
        return true;
    }

}