#calle
insert into calle (numeroCalle,letraCalle,bis,sur) values
(80,"b",0, "0");
#carrera
insert into carrera (numeroCarrera,letraCarrera,bis,este) values
("30","a",0,0);
#direccion
insert into direccion(Calle_idCalle,Carrera_idCarrera) values
(1,1);


#rol 
insert into rol(nombreRol) values ("Generente Designado");

#usuario
insert into usuario(usuario,Rol_idRol,contraseña) values ("admin",1,"1234");

#sedes
insert into sede(Direccion_idDireccion,nombreSede) values
(1,"Sede 80");

#permisos
insert into permiso(nombrePermiso) values("prueba");