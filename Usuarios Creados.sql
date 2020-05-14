#calle
insert into calle (numeroCalle,letraCalle,bis,sur) values
(80,"b",0, "0");
#carrera
insert into carrera (numeroCarrera,letraCarrera,bis,este) values
("30","a",0,0);
#direccion
insert into direccion(Calle_idCalle,Carrera_idCarrera) values
(1,1);

#values(0,1,"admin","","","2000-01-01","Colombiano","n");

#Personas - empleados
call insertarEmpleado(0,1,"admin","","","","2000-01-01","Colombiano","n",1,"Administrador","Gerente",0);

#rol 
insert into rol(nombreRol) values ("Gerente Designado");
#delete from rol where idRol=1;
#usuario
insert into usuario(usuario,Rol_idRol,contraseña) values ("admin",1,"1234");

#sedes
insert into sede(Direccion_idDireccion,nombreSede) values
(1,"Sede 80");

set SQL_SAFE_UPDATES =0;
#permisos
insert into permiso(nombrePermiso) values("prueba");
delete from permiso where nombrePermiso ="prueba";
insert into rol_has_permiso(Rol_idRol,Permiso_idPermiso) values(1,1);

select p.nombrePermiso as nombrePermiso from permiso as p 
inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso
inner join rol as r on r.idRol = rp.Rol_idRol
where r.nombreRol = "Gerente Designado";