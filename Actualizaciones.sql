/*Empleado*/
update empleado
set emplado.cargo="cargo",empleado.profesion="profesion",empleado.salario=0, Sede_idSede=0
where empleado.Persona_cedula=0;

update persona
set primerNombre="",segundoNombre="",primerApellido="",segundoApellido="",fechaNacimiento="",nacionalidad="",genero="", Direccion_idDireccion=0
where persona.cedula ="";


/*Usuario*/
update usuario 
set Empleado_Persona_cedula = 0, Rol_idRol=1,contraseña="4321"
where usuario="admin"; 

/*Dirección*/
call actualizarDireccion(1,1,1,2,"a",0,0,1,"b",0,0);