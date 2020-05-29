create table auditoriaEmpleados
(
fechaAuditoria datetime,
Persona_cedula int,
Sede_idSede int,
profesion varchar(20),
cargo varchar(20),
salario double
);

create trigger TR_01 
after insert ON Empleado for each row
insert into auditoriaEmpleados (fechaAuditoria,Persona_cedula,Sede_idSede,profesion,cargo,salario) 
values ((select now()), new.Persona_cedula,new.Sede_idSede,new.profesion,new.cargo,new.salario);




create trigger TR_02 
after insert ON FacturaVenta for each row
insert into auditoriaFacturas (fechaInsercionFactura,id_fventa,Empleado_Persona_cedula,Cliente_Persona_cedula,iva,totalVenta,fecha) 
values ((select now()),new.id_fventa,new.Empleado_Persona_cedula,new.Cliente_Persona_cedula,new.iva,new.totalVenta,new.fecha);

create table auditoriaFacturas
(
fechaInsercionFactura datetime,
id_fventa int,
Empleado_Persona_cedula int,
Cliente_Persona_cedula int,
iva double,
totalVenta double,
fecha date
);

create TABLE auditoriaBicicleta
 (
  fechaInsercionBicicleta datetime,
  marcoSerial VARCHAR(20) NOT NULL,
  grupoMecanico VARCHAR(20) NOT NULL,
  color VARCHAR(20) NULL,
  marca VARCHAR(20) NULL,
  estado VARCHAR(60) NULL
);
create trigger TR_03 
after insert ON Bicicleta for each row
insert into auditoriaBicicleta (fechaInsercionBicicleta,marcoSerial,grupoMecanico,color,marca,estado) 
values ((select now()),new.marcoSerial,new.grupoMecanico,new.color,new.marca,new.estado);


create TABLE auditoriaCliente 
(
  fechaInsercionCliente datetime,
  Persona_cedula INTEGER UNSIGNED NOT NULL

);

create trigger TR_04 
after insert ON Cliente for each row
insert into auditoriaCliente (fechaInsercionCliente,Persona_cedula) 
values ((select now()),new.Persona_cedula);


CREATE TABLE auditoriaEmpresaMantenimiento 
(
  fechaInsercionEmpresaMantenimiento datetime,
  id_empresaMantenimiento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombreEmpresaMantemiento VARCHAR(40) NULL,
  PRIMARY KEY(id_empresaMantenimiento),
  INDEX EmpresaMantenimiento_FKIndex1(Direccion_idDireccion)
  
);

create trigger TR_05 
after insert ON EmpresaMantenimiento for each row
insert into auditoriaEmpresaMantenimiento (fechaInsercionEmpresaMantenimiento,id_empresaMantenimiento,Direccion_idDireccion,nombreEmpresaMantemiento) 
values ((select now()),new.id_empresaMantenimiento,new.Direccion_idDireccion,new.nombreEmpresaMantemiento);


CREATE TABLE auditoriaEquipoOficina (
  fechaInsercionEquipoOficina datetime,
  Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  descripcion VARCHAR(70) NULL,
  PUC VARCHAR(10) NULL,
  PRIMARY KEY(Objeto_idObjeto),
  INDEX EquipoOficina_FKIndex1(Sede_idSede),
  INDEX EquipoOficina_FKIndex2(Objeto_idObjeto)
  
);

create trigger TR_06 
after insert ON EquipoOficina for each row
insert into auditoriaEquipoOficina (fechaInsercionEquipoOficina,Objeto_idObjeto,Sede_idSede,descripcion,PUC) 
values ((select now()),new.Objeto_idObjeto,new.Sede_idSede,new.descripcion,new.PUC);


CREATE TABLE AuditoriaInventario (
  FechaInsercionInventario datetime,
  id_inventario INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id_inventario),
  INDEX Inventario_FKIndex1(Sede_idSede)
  
);

create trigger TR_07
after insert ON Inventario for each row
insert into AuditoriaInventario (FechaInsercionInventario,id_inventario,Sede_idSede) 
values ((select now()),new.id_inventario,new.Sede_idSede);


create TABLE AuditoriaMantenientoBicicleta 
(
  fechaInsercionMantenimiento datetime,
  idMantenimientoBicicleta INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Bicicleta_marcoSerial VARCHAR(20) NOT NULL,
  FacturaVenta_id_fventa INTEGER UNSIGNED NOT NULL,
  descripcion VARCHAR(180) NULL,
  fechaEntrega DATE NULL,
  PRIMARY KEY(idMantenimientoBicicleta),
  INDEX MantenientoBici_FKIndex1(FacturaVenta_id_fventa),
  INDEX MantenientoBicicleta_FKIndex2(Bicicleta_marcoSerial)
);

create trigger TR_08
after insert ON MantenientoBicicleta for each row
insert into AuditoriaMantenientoBicicleta (fechaInsercionMantenimiento,idMantenimientoBicicleta,Bicicleta_marcoSerial,FacturaVenta_id_fventa,descripcion,fechaEntrega) 
values ((select now()),new.idMantenimientoBicicleta,new.Bicicleta_marcoSerial,new.FacturaVenta_id_fventa,new.descripcion,new.fechaEntrega);


CREATE TABLE AuditoriaMantenimientoTaller (
  fechaInsercionMantenimientoTaller datetime,
  idMantenimiento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  EmpresaMantenimiento_id_empresaMantenimiento INTEGER UNSIGNED NOT NULL,
  Taller_idTaller INTEGER UNSIGNED NOT NULL,
  fecha DATE NULL,
  factura VARCHAR(40) NULL,
  PRIMARY KEY(idMantenimiento),
  INDEX Mantenimiento_FKIndex1(Taller_idTaller),
  INDEX Mantenimiento_FKIndex2(EmpresaMantenimiento_id_empresaMantenimiento)
);

create trigger TR_09
after insert ON MantenimientoTaller for each row
insert into AuditoriaMantenimientoTaller (fechaInsercionMantenimientoTaller,idMantenimiento,EmpresaMantenimiento_id_empresaMantenimiento,Taller_idTaller,fecha,factura) 
values ((select now()),new.idMantenimiento,new.EmpresaMantenimiento_id_empresaMantenimiento,new.Taller_idTaller,new.fecha,new.factura);


CREATE TABLE AuditoriaMecanicoPlanta (
  fechaInsercionMecanicoPlanta datetime,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  jefeMecanico INTEGER UNSIGNED NULL,
  PRIMARY KEY(Empleado_Persona_cedula),
  INDEX MecanicoPlanta_FKIndex1(Empleado_Persona_cedula)
);

create trigger TR_10
after insert ON MecanicoPlanta for each row
insert into AuditoriaMecanicoPlanta (fechaInsercionMecanicoPlanta,Empleado_Persona_cedula,jefeMecanico) 
values ((select now()),new.Empleado_Persona_cedula,new.jefeMecanico);


CREATE TABLE AuditoriaMercancia (
  fechaInsercionMercancia datetime,
  Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  Inventario_id_inventario INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(40) NULL,
  valor_adq INTEGER UNSIGNED NULL,
  precio_venta INTEGER UNSIGNED NULL,
  cantidad INTEGER UNSIGNED NULL,
  PRIMARY KEY(Objeto_idObjeto),
  INDEX Mercancia_FKIndex1(Inventario_id_inventario),
  INDEX Mercancia_FKIndex2(Objeto_idObjeto)
);

create trigger TR_11
after insert ON Mercancia for each row
insert into AuditoriaMercancia (fechaInsercionMercancia,Objeto_idObjeto,Inventario_id_inventario,nombre,valor_adq,precio_venta,cantidad) 
values ((select now()),new.Objeto_idObjeto,new.Inventario_id_inventario,new.nombre,new.valor_adq,new.precio_venta,new.cantidad);


CREATE TABLE AuditoriaObjeto (
  fechaInsercionObjeto datetime,
  idObjeto INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  marca VARCHAR(40) NULL,
  años_garantia INTEGER UNSIGNED NULL,
  PRIMARY KEY(idObjeto)
);

create trigger TR_12
after insert ON Objeto for each row
insert into AuditoriaObjeto (fechaInsercionObjeto,idObjeto,marca,años_garantia) 
values ((select now()),new.idObjeto,new.marca,new.años_garantia);


CREATE TABLE AuditoriaPermiso (
  fechaInsercion datetime,
  idPermiso INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombrePermiso VARCHAR(40) NULL,
  PRIMARY KEY(idPermiso)
);

create trigger TR_13
after insert ON Permiso for each row
insert into AuditoriaPermiso (fechaInsercion,idPermiso,nombrePermiso) 
values ((select now()),new.idPermiso,new.nombrePermiso);


CREATE TABLE AuditoriaPersona (
  fechaInsercionPersona datetime,
  cedula varchar(18) NOT NULL,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  primerNombre VARCHAR(20) NULL,
  segundoNombre VARCHAR(20) NULL,
  primerApellido VARCHAR(20) NULL,
  segundoApellido VARCHAR(20) NULL,
  fechaNacimiento DATE NULL,
  nacionalidad VARCHAR(15) NULL,
  genero VARCHAR(1) NULL,
  PRIMARY KEY(cedula),
  INDEX Persona_FKIndex1(Direccion_idDireccion)
);

create trigger TR_14
after insert ON Persona for each row
insert into AuditoriaPersona (fechaInsercionPersona,cedula,Direccion_idDireccion,primerNombre,segundoNombre,primerApellido,segundoApellido,fechaNacimiento,nacionalidad,genero) 
values ((select now()),new.cedula,new.Direccion_idDireccion,new.primerNombre,new.segundoNombre,new.primerApellido,new.segundoApellido,new.fechaNacimiento,new.nacionalidad,new.genero);


CREATE TABLE PeticionEmpleado (
  idPeticionEmpleado INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  peticion VARCHAR(40) NULL,
  aprobado BOOL NULL,
  PRIMARY KEY(idPeticionEmpleado),
  INDEX PeticionEmpleado_FKIndex1(Empleado_Persona_cedula)
);

CREATE TABLE AuditoriaPeticionEmpleado (
  fechaInsercionPeticion datetime,
  idPeticionEmpleado INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  peticion VARCHAR(40) NULL,
  aprobado BOOL NULL,
  PRIMARY KEY(idPeticionEmpleado),
  INDEX PeticionEmpleado_FKIndex1(Empleado_Persona_cedula)
);

create trigger TR_15
after insert ON PeticionEmpleado for each row
insert into AuditoriaPeticionEmpleado (fechaInsercionPeticion,idPeticionEmpleado,Empleado_Persona_cedula,peticion,aprobado) 
values ((select now()),new.idPeticionEmpleado,new.Empleado_Persona_cedula,new.peticion,new.aprobado);

CREATE TABLE AuditoriaProveedor (
  fechaInsercionProveedor datetime,
  idProveedor INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(20) NULL,
  PRIMARY KEY(idProveedor),
  INDEX Proveedor_FKIndex1(Direccion_idDireccion)
);

create trigger TR_16
after insert ON Proveedor for each row
insert into AuditoriaProveedor (fechaInsercionProveedor,idProveedor,Direccion_idDireccion,nombre) 
values ((select now()),new.idProveedor,new.Direccion_idDireccion,new.nombre);


create TABLE AuditoriaRol 
(
  fechaInsercionRol datetime,
  idRol INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombreRol VARCHAR(30) NULL,
  nombreCorto varchar(10) null,
  PRIMARY KEY(idRol)
);

create trigger TR_17
after insert ON Rol for each row
insert into AuditoriaRol (fechaInsercionRol,idRol,nombreRol,nombreCorto) 
values ((select now()),new.idRol,new.nombreRol,new.nombreCorto);

create TABLE AuditoriaSede (
  fechaInsercionSede datetime,
  idSede INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombreSede VARCHAR(30) NULL,
  PRIMARY KEY(idSede),
  INDEX Sede_FKIndex1(Direccion_idDireccion)
);

create trigger TR_18
after insert ON Sede for each row
insert into AuditoriaSede (fechaInsercionSede,idSede,Direccion_idDireccion,nombreSede) 
values ((select now()),new.idSede,new.Direccion_idDireccion,new.nombreSede);


CREATE TABLE AuditoriaTaller (
  fechaInsercionTaller datetime,
  idTaller INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  totalVentas INTEGER UNSIGNED NULL,
  PRIMARY KEY(idTaller),
  INDEX Taller_FKIndex1(Sede_idSede)
);

create trigger TR_19
after insert ON Taller for each row
insert into AuditoriaTaller (fechaInsercionTaller,idTaller,Sede_idSede,totalVentas) 
values ((select now()),new.idTaller,new.Sede_idSede,new.totalVentas);

CREATE TABLE AuditoriaUsuario (
  fechaInsercionUsuario datetime,
  usuario VARCHAR(30) NOT NULL,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  Rol_idRol INTEGER UNSIGNED NOT NULL,
  contraseña VARCHAR(30) NULL,
  PRIMARY KEY(usuario),
  INDEX Usuario_FKIndex1(Rol_idRol),
  INDEX Usuario_FKIndex2(Empleado_Persona_cedula)
);

create trigger TR_20
after insert ON Usuario for each row
insert into AuditoriaUsuario (fechaInsercionUsuario,usuario,Empleado_Persona_cedula,Rol_idRol,contraseña) 
values ((select now()),new.usuario,new.Empleado_Persona_cedula,new.Rol_idRol,new.contraseña);