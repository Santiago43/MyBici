#calle
insert into calle (numeroCalle,letraCalle,bis,sur) values
(80,"b",0, "0");
#carrera
insert into carrera (numeroCarrera,letraCarrera,bis,este) values
("30","a",0,0);
#direccion
insert into direccion(Calle_idCalle,Carrera_idCarrera) values
((select idCalle from calle where numeroCalle=80 and letraCalle ="b" and bis=0 and sur=0),
(select idCarrera from carrera where numeroCarrera= 30 and letraCarrera="a" and bis=0 and este =0));

select * from direccion;
#values(0,1,"admin","","","2000-01-01","Colombiano","n");

#Personas - empleados
call insertarEmpleado(0,1,"admin","","","","2000-01-01","Colombiano","n",1,"Administrador","Gerente",0);

#rol 
insert into rol(nombreRol) values ("Gerente Designado");
#delete from rol where idRol=1;
#usuario
insert into usuario(usuario,Empleado_Persona_cedula,Rol_idRol,contraseña) values ("admin",0,1,"1234");
insert into Valores_Financieros values (0.19);

set SQL_SAFE_UPDATES =0;
#permisos
insert into permiso(nombrePermiso) values("prueba");
#delete from permiso where idPermiso =2;
select * from permiso;
#delete from permiso where nombrePermiso ="prueba";



/*Sedes*/
call crearSede(1,"Sede 80");

/*Agregar taller*/

insert into taller (Sede_idSede,totalVentas) values(1,0); 

/*Agregar proveedor*/
insert into proveedor (Direccion_idDireccion,nombre) values (1,"Frenos LTDA");
insert into proveedor (Direccion_idDireccion,nombre) values (1,"Amoblando");

/*Crear permisos*/

insert into permiso (nombrePermiso) values ("Prueba2");
insert into permiso (nombrePermiso) values ("*");
/*Agregar permiso al rol*/
insert into rol_has_permiso(Rol_idRol,Permiso_idPermiso) 
values((select idRol from rol where nombreRol="Gerente Designado"),(select idPermiso from permiso where nombrePermiso="prueba"));
#insert into rol_has_permiso(Rol_idRol,Permiso_idPermiso) values(1,1);
/*insert into rol_has_permiso (Rol_idRol,Permiso_idPermiso) 
values(1,2);*/

/*Agregar permiso al usuario*/
#insert into usuario_has_permiso() values();


/*Agregar permiso a usuario*/
insert into usuario_has_permiso (usuario_usuario,Permiso_idPermiso)
values ("admin",(select idPermiso from permiso where nombrePermiso="*"));

select * from usuario;
select * from permiso;
select * from usuario_has_permiso;
/*Insertar a inventario*/
call insertarMercanciaAInventario("Ashima",1,1,"Tornillos disco",20500,25000,12,2);

/*Insertar como equipo de oficina
in marca varchar(40),
in años_garantia int,
in idSede int,
in descripcion varchar(70), 
in PUC varchar(10),
in valorAdquisicion double,
in fechaAdquisicion date
*/
call insertarEquipoOficina("Berloni",1,1,"Moderna mesa de centro con cuatro cajones y dos puff de asiento tapizado","152405",1398000,"2020-05-25",3);

/*Tipo de telefono*/

insert into telefono (tipo) values ("fijo");
insert into telefono (tipo) values ("celular");
insert into telefono (tipo) values ("pbx");
insert into telefono (tipo) values ("nacional");

/*Agregar telefono a persona*/

call agregarTelefonoAPersona ("fijo","0","7292821");


