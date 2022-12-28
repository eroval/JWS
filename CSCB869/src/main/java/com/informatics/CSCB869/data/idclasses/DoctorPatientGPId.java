package com.informatics.CSCB869.data.idclasses;

import java.io.Serializable;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;

public class DoctorPatientGPId implements Serializable {
    protected long doctorId;
    protected long patientId;

    public DoctorPatientGPId(){

    }

    public DoctorPatientGPId(long patientId, long doctorId){
        this.setPatient(patientId);
        this.setDoctor(doctorId);
    }

    private void setPatient(long patientId) { this.patientId=patientId;}
    private void setDoctor(long doctorId) { this.doctorId=doctorId;}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (doctorId ^ (doctorId >>> 32));
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
        DoctorPatientGPId other = (DoctorPatientGPId) obj;
        if (doctorId != other.doctorId)
            return false;
        if (patientId != other.patientId)
            return false;
        return true;
    }

    

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     DoctorPatientGPId that = (DoctorPatientGPId) o;
    //     return personId.equals(that.personId) && doctorId.equals(that.doctorId);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(personId, doctorId);
    // }
}