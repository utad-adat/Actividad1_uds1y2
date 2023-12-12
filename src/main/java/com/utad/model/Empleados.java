package com.utad.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "empleados")
public class Empleados {

    private List<Empleado> empleadosList;

    public Empleados() {
        this.empleadosList = new ArrayList<>();
    }

    public List<Empleado> getEmpleados() {
        return empleadosList;
    }

    @XmlElement(name="empleado")
    public void setEmpleados(List<Empleado> empleados) {
        this.empleadosList = empleados;
    }
}