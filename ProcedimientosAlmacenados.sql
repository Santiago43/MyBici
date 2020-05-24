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



/*
Procedimiento almacenado para insertar direcciones
*/
delimiter $$

create procedure insertarDireccion (
in numeroCalle int,
in letraCalle varchar(1),
in bisCalle bool,
in sur bool,
in numeroCarrera int,
in letraCarrera varchar(1),
in bisCarrera bool,
in este bool
)
begin
insert into calle (numeroCalle,letraCalle,bis,sur) values
(numeroCalle,letraCalle,bisCalle, sur);
insert into carrera (numeroCarrera,letraCarrera,bis,este) values
(numeroCarrera,letraCarrera,bisCarrera,este);
insert into direccion(Calle_idCalle,Carrera_idCarrera) values
((select idCalle from calle order by idCalle desc limit 1),
(select idCarrera from carrera order by idCarrera desc limit 1));
end $$


/*
Actualizar dirección
*/
delimiter $$
create procedure actualizarDireccion (
in _idDireccion int,
in _idCalle int,
in _idCarrera int,
in _numeroCalle int,
in _letraCalle varchar(1),
in _bisCalle bool,
in _sur bool,
in _numeroCarrera int,
in _letraCarrera varchar(1),
in _bisCarrera bool,
in _este bool
)
begin
update calle 
set numeroCalle = _numeroCalle, letraCalle = _letraCalle, bis= _bisCalle, sur=_sur 
where idCalle = _idCalle;

update carrera 
set numeroCarrera =_numeroCarrera, letraCarrera = _letraCarrera, bis=_bisCarrera, este=_este
where idCarrera = _idCarrera; 

update direccion
set Calle_idCalle = _idCalle,Carrera_idCarrera=_idCarrera 
where idDireccion = _idDireccion;
end $$


/*Crear sede con su inventario*/
delimiter $$

create procedure crearSede (
in idDireccion int,
in nombreSede varchar (30)
)
begin
insert into sede (Direccion_idDireccion,nombreSede) values(idDireccion,nombreSede);
insert into inventario(Sede_idSede) values ((select idSede from sede order by idSede desc limit 1));
end $$


/*Crear cliente*/
delimiter $$

create procedure crearCliente (
in cedula int,
in idDireccion int, 
in primerNombre varchar(20),
in segundoNombre varchar(20),
in primerApellido varchar(20), 
in segundoApellido varchar(20),
in fechaNacimiento date,
in nacionalidad varchar(15),
in genero varchar(1)
)
begin
insert into persona (cedula,Direccion_idDireccion,primerNombre,segundoNombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero)
values(cedula,idDireccion,primerNombre,segundoNombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero);
insert into cliente (Persona_cedula) values (cedula);
end $$