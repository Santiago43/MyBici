delimiter $$

create procedure insertarEmpleado (
in cedula int,
in idDireccion int, 
in primerNombre varchar(20),
in segundoNombre varchar(20),
in primerApellido varchar(20), 
in segundoApellido varchar(20),
in idCiudad integer,
in telefono varchar(13), 
in direccion varchar(15)
)
begin
insert into persona (cedula,Direccion_idDireccion,nombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero)
values(0,1,"admin","","","2000-01-01","Colombiano","n");
insert into Persona (primerNombre_persona, segundoNombre_persona, primerApellido_persona, segundoApellido_persona) values (primerNombre,segundoNombre,primerApellido,segundoApellido);
insert into Clientes (Persona_idPersona, Ciudad_idCiudad, telefono_cliente,direccion_cliente) values ((select idPersona from Persona order by idPersona desc limit 1),idCiudad,telefono,direccion);
end $$