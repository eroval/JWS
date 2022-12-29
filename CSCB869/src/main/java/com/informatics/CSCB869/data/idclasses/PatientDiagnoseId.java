package com.informatics.CSCB869.data.idclasses;

import java.io.Serializable;

public class PatientDiagnoseId implements Serializable {
    protected long patientId;
    protected long diagnoseId;

    public PatientDiagnoseId(){

    }

    public PatientDiagnoseId(long patientId, long diagnoseId){
        this.setPatient(patientId);
        this.setDiagnose(diagnoseId);
    }

    private void setPatient(long patientId) { this.patientId=patientId;}
    private void setDiagnose(long diagnoseId) { this.diagnoseId=diagnoseId;}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (patientId ^ (patientId >>> 32));
        result = prime * result + (int) (diagnoseId ^ (diagnoseId >>> 32));
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
        PatientDiagnoseId other = (PatientDiagnoseId) obj;
        if (patientId != other.patientId)
            return false;
        if (diagnoseId != other.diagnoseId)
            return false;
        return true;
    }

}