delimiter $$

create procedure insertarEmpleado (
in cedula int,
in idDireccion int, 
in primerNombre varchar(20),
in segundoNombre varchar(20),
in primerApellido varchar(20), 
in segundoApellido varchar(20),
in fechaNacimiento date,
in nacionalidad varchar(15),
in genero varchar(1),
in idSede int,
in profesion varchar(20),
in cargo varchar(20),
in salario double
)
begin
insert into persona (cedula,Direccion_idDireccion,primerNombre,segundoNombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero)
values(cedula,idDireccion,primerNombre,segundoNombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero);
insert into empleado (Persona_cedula, Sede_idSede, profesion,cargo,salario) values (cedula, idDireccion,profesion,cargo,salario);
end $$







#values(0,1,"admin","","","2000-01-01","Colombiano","n");