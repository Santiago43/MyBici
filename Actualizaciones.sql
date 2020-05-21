update empleado
set emplado.cargo="cargo",empleado.profesion="profesion",empleado.salario=0
where empleado.Persona_cedula=0;

update persona
set persona.primerNombre="",persona.segundoNombre="",persona.primerApellido="",persona.segundoApellido="", persona.fechaNacimiento="",persona.genero="";