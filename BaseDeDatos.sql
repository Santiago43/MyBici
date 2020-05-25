drop database if exists mybici;
create database mybici;
use mybici;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE Permiso (
  idPermiso INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombrePermiso VARCHAR(40) NULL,
  PRIMARY KEY(idPermiso)
);

CREATE TABLE Objeto (
  idObjeto INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  marca VARCHAR(40) NULL,
  años_garantia INTEGER UNSIGNED NULL,
  PRIMARY KEY(idObjeto)
);

CREATE TABLE Telefono (
  id_telefono INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(10) NULL,
  PRIMARY KEY(id_telefono)
);

CREATE TABLE Rol (
  idRol INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombreRol VARCHAR(30) NULL,
  PRIMARY KEY(idRol)
);

CREATE TABLE Carrera (
  idCarrera INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  numeroCarrera INTEGER UNSIGNED NULL,
  letraCarrera VARCHAR(1) NULL,
  bis BOOL NULL,
  este BOOL NULL,
  PRIMARY KEY(idCarrera)
);

CREATE TABLE Calle (
  idCalle INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  numeroCalle INTEGER UNSIGNED NULL,
  letraCalle VARCHAR(1) NULL,
  bis BOOL NULL,
  sur BOOL NULL,
  PRIMARY KEY(idCalle)
);

CREATE TABLE Bicicleta (
  marcoSerial VARCHAR(20) NOT NULL,
  grupoMecanico VARCHAR(20) NOT NULL,
  color VARCHAR(20) NULL,
  marca VARCHAR(20) NULL,
  estado VARCHAR(60) NULL,
  PRIMARY KEY(marcoSerial)
);

CREATE TABLE Rol_has_Permiso (
  Rol_idRol INTEGER UNSIGNED NOT NULL,
  Permiso_idPermiso INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Rol_idRol, Permiso_idPermiso),
  INDEX Rol_has_Permiso_FKIndex1(Rol_idRol),
  INDEX Rol_has_Permiso_FKIndex2(Permiso_idPermiso),
  FOREIGN KEY(Rol_idRol)
    REFERENCES Rol(idRol)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Permiso_idPermiso)
    REFERENCES Permiso(idPermiso)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Direccion (
  idDireccion INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Calle_idCalle INTEGER UNSIGNED NOT NULL,
  Carrera_idCarrera INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(idDireccion),
  INDEX Direccion_FKIndex1(Carrera_idCarrera),
  INDEX Direccion_FKIndex2(Calle_idCalle),
  FOREIGN KEY(Carrera_idCarrera)
    REFERENCES Carrera(idCarrera)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Calle_idCalle)
    REFERENCES Calle(idCalle)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Persona (
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
  INDEX Persona_FKIndex1(Direccion_idDireccion),
  FOREIGN KEY(Direccion_idDireccion)
    REFERENCES Direccion(idDireccion)
      ON DELETE cascade
      ON UPDATE cascade
);



CREATE TABLE EmpresaMantenimiento (
  id_empresaMantenimiento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombreEmpresaMantemiento VARCHAR(40) NULL,
  PRIMARY KEY(id_empresaMantenimiento),
  INDEX EmpresaMantenimiento_FKIndex1(Direccion_idDireccion),
  FOREIGN KEY(Direccion_idDireccion)
    REFERENCES Direccion(idDireccion)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Cliente (
  Persona_cedula varchar(18) NOT NULL,
  PRIMARY KEY(Persona_cedula),
  INDEX Cliente_FKIndex1(Persona_cedula),
  FOREIGN KEY(Persona_cedula)
    REFERENCES Persona(cedula)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE Sede (
  idSede INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombreSede VARCHAR(30) NULL,
  PRIMARY KEY(idSede),
  INDEX Sede_FKIndex1(Direccion_idDireccion),
  FOREIGN KEY(Direccion_idDireccion)
    REFERENCES Direccion(idDireccion)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Proveedor (
  idProveedor INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Direccion_idDireccion INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(20) NULL,
  PRIMARY KEY(idProveedor),
  INDEX Proveedor_FKIndex1(Direccion_idDireccion),
  FOREIGN KEY(Direccion_idDireccion)
    REFERENCES Direccion(idDireccion)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Inventario (
  id_inventario INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id_inventario),
  INDEX Inventario_FKIndex1(Sede_idSede),
  FOREIGN KEY(Sede_idSede)
    REFERENCES Sede(idSede)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE EmpresaMantenimiento_has_Telefono (
  Telefono_id_telefono INTEGER UNSIGNED NOT NULL,
  EmpresaMantenimiento_id_empresaMantenimiento INTEGER UNSIGNED NOT NULL,
  numeroTelefono varchar(13) NULL,
  PRIMARY KEY(Telefono_id_telefono, EmpresaMantenimiento_id_empresaMantenimiento),
  INDEX EmpresaMantenimiento_has_Telefono_FKIndex1(EmpresaMantenimiento_id_empresaMantenimiento),
  INDEX EmpresaMantenimiento_has_Telefono_FKIndex2(Telefono_id_telefono),
  FOREIGN KEY(EmpresaMantenimiento_id_empresaMantenimiento)
    REFERENCES EmpresaMantenimiento(id_empresaMantenimiento)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Telefono_id_telefono)
    REFERENCES Telefono(id_telefono)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE EquipoOficina (
  Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  descripcion VARCHAR(70) NULL,
  PUC VARCHAR(10) NULL,
  PRIMARY KEY(Objeto_idObjeto),
  INDEX EquipoOficina_FKIndex1(Sede_idSede),
  INDEX EquipoOficina_FKIndex2(Objeto_idObjeto),
  FOREIGN KEY(Sede_idSede)
    REFERENCES Sede(idSede)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Objeto_idObjeto)
    REFERENCES Objeto(idObjeto)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Empleado (
  Persona_cedula varchar(18) NOT NULL,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  profesion VARCHAR(20) NULL,
  cargo VARCHAR(20) NULL,
  salario DOUBLE NULL,
  PRIMARY KEY(Persona_cedula),
  INDEX Empleado_FKIndex1(Sede_idSede),
  INDEX Empleado_FKIndex2(Persona_cedula),
  FOREIGN KEY(Sede_idSede)
    REFERENCES Sede(idSede)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Persona_cedula)
    REFERENCES Persona(cedula)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Objeto_has_Proveedor (
  Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  Proveedor_idProveedor INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Objeto_idObjeto, Proveedor_idProveedor),
  INDEX Objeto_has_Proveedor_FKIndex1(Objeto_idObjeto),
  INDEX Objeto_has_Proveedor_FKIndex2(Proveedor_idProveedor),
  FOREIGN KEY(Objeto_idObjeto)
    REFERENCES Objeto(idObjeto)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Proveedor_idProveedor)
    REFERENCES Proveedor(idProveedor)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Mercancia (
  Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  Inventario_id_inventario INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(40) NULL,
  valor_adq INTEGER UNSIGNED NULL,
  precio_venta INTEGER UNSIGNED NULL,
  cantidad INTEGER UNSIGNED NULL,
  PRIMARY KEY(Objeto_idObjeto),
  INDEX Mercancia_FKIndex1(Inventario_id_inventario),
  INDEX Mercancia_FKIndex2(Objeto_idObjeto),
  FOREIGN KEY(Inventario_id_inventario)
    REFERENCES Inventario(id_inventario)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Objeto_idObjeto)
    REFERENCES Objeto(idObjeto)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Proveedor_has_Telefono (
  Telefono_id_telefono INTEGER UNSIGNED NOT NULL,
  Proveedor_idProveedor INTEGER UNSIGNED NOT NULL,
  numeroTelefono varchar(13) NULL,
  PRIMARY KEY(Telefono_id_telefono, Proveedor_idProveedor),
  INDEX Telefono_has_Proveedor_FKIndex1(Telefono_id_telefono),
  INDEX Telefono_has_Proveedor_FKIndex2(Proveedor_idProveedor),
  FOREIGN KEY(Telefono_id_telefono)
    REFERENCES Telefono(id_telefono)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Proveedor_idProveedor)
    REFERENCES Proveedor(idProveedor)
      ON DELETE cascade
      ON UPDATE cascade
);


CREATE TABLE FacturaVenta (
  id_fventa INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  Cliente_Persona_cedula varchar(18) NOT NULL,
  iva DOUBLE NULL,
  totalVenta DOUBLE NULL,
  fecha DATE NULL,
  PRIMARY KEY(id_fventa),
  INDEX FacturaVenta_FKIndex1(Empleado_Persona_cedula),
  INDEX FacturaVenta_FKIndex2(Cliente_Persona_cedula),
  FOREIGN KEY(Empleado_Persona_cedula)
    REFERENCES Empleado(Persona_cedula)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Cliente_Persona_cedula)
    REFERENCES Cliente(Persona_cedula)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Persona_has_Telefono (
  Telefono_id_telefono INTEGER UNSIGNED NOT NULL,
  Persona_cedula varchar(18) NOT NULL,
  numeroTelefono varchar(13) NULL,
  PRIMARY KEY(Telefono_id_telefono, Persona_cedula),
  INDEX Persona_has_Telefono_FKIndex1(Persona_cedula),
  INDEX Persona_has_Telefono_FKIndex2(Telefono_id_telefono),
  FOREIGN KEY(Persona_cedula)
    REFERENCES Persona(cedula)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Telefono_id_telefono)
    REFERENCES Telefono(id_telefono)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE PeticionEmpleado (
  idPeticionEmpleado INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  peticion VARCHAR(40) NULL,
  aprobado BOOL NULL,
  PRIMARY KEY(idPeticionEmpleado),
  INDEX PeticionEmpleado_FKIndex1(Empleado_Persona_cedula),
  FOREIGN KEY(Empleado_Persona_cedula)
    REFERENCES Empleado(Persona_cedula)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Taller (
  idTaller INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Sede_idSede INTEGER UNSIGNED NOT NULL,
  totalVentas INTEGER UNSIGNED NULL,
  PRIMARY KEY(idTaller),
  INDEX Taller_FKIndex1(Sede_idSede),
  FOREIGN KEY(Sede_idSede)
    REFERENCES Sede(idSede)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE MecanicoPlanta (
  Empleado_Persona_cedula varchar(18) NOT NULL,
  jefeMecanico INTEGER UNSIGNED NULL,
  PRIMARY KEY(Empleado_Persona_cedula),
  INDEX MecanicoPlanta_FKIndex1(Empleado_Persona_cedula),
  FOREIGN KEY(Empleado_Persona_cedula)
    REFERENCES Empleado(Persona_cedula)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Venta_has_Mercancia (
  FacturaVenta_id_fventa INTEGER UNSIGNED NOT NULL,
  Mercancia_Objeto_idObjeto INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(FacturaVenta_id_fventa, Mercancia_Objeto_idObjeto),
  INDEX Venta_has_Mercancia_FKIndex1(FacturaVenta_id_fventa),
  INDEX Venta_has_Mercancia_FKIndex2(Mercancia_Objeto_idObjeto),
  FOREIGN KEY(FacturaVenta_id_fventa)
    REFERENCES FacturaVenta(id_fventa)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Mercancia_Objeto_idObjeto)
    REFERENCES Mercancia(Objeto_idObjeto)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Usuario (
  usuario VARCHAR(30) NOT NULL,
  Empleado_Persona_cedula varchar(18) NOT NULL,
  Rol_idRol INTEGER UNSIGNED NOT NULL,
  contraseña VARCHAR(30) NULL,
  PRIMARY KEY(usuario),
  INDEX Usuario_FKIndex1(Rol_idRol),
  INDEX Usuario_FKIndex2(Empleado_Persona_cedula),
  FOREIGN KEY(Rol_idRol)
    REFERENCES Rol(idRol)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Empleado_Persona_cedula)
    REFERENCES Empleado(Persona_cedula)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE MantenimientoTaller (
  idMantenimiento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  EmpresaMantenimiento_id_empresaMantenimiento INTEGER UNSIGNED NOT NULL,
  Taller_idTaller INTEGER UNSIGNED NOT NULL,
  fecha DATE NULL,
  factura VARCHAR(40) NULL,
  PRIMARY KEY(idMantenimiento),
  INDEX Mantenimiento_FKIndex1(Taller_idTaller),
  INDEX Mantenimiento_FKIndex2(EmpresaMantenimiento_id_empresaMantenimiento),
  FOREIGN KEY(Taller_idTaller)
    REFERENCES Taller(idTaller)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(EmpresaMantenimiento_id_empresaMantenimiento)
    REFERENCES EmpresaMantenimiento(id_empresaMantenimiento)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE MantenientoBicicleta (
  idMantenimientoBicicleta INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Bicicleta_marcoSerial VARCHAR(20) NOT NULL,
  FacturaVenta_id_fventa INTEGER UNSIGNED NOT NULL,
  descripcion VARCHAR(180) NULL,
  valorEstimado INTEGER UNSIGNED NULL,
  fechaEntrega DATE NULL,
  PRIMARY KEY(idMantenimientoBicicleta),
  INDEX MantenientoBici_FKIndex1(FacturaVenta_id_fventa),
  INDEX MantenientoBicicleta_FKIndex2(Bicicleta_marcoSerial),
  FOREIGN KEY(FacturaVenta_id_fventa)
    REFERENCES FacturaVenta(id_fventa)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Bicicleta_marcoSerial)
    REFERENCES Bicicleta(marcoSerial)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE Usuario_has_Permiso (
  Usuario_usuario VARCHAR(30) NOT NULL,
  Permiso_idPermiso INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Usuario_usuario, Permiso_idPermiso),
  INDEX Usuario_has_Permiso_FKIndex1(Usuario_usuario),
  INDEX Usuario_has_Permiso_FKIndex2(Permiso_idPermiso),
  FOREIGN KEY(Usuario_usuario)
    REFERENCES Usuario(usuario)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(Permiso_idPermiso)
    REFERENCES Permiso(idPermiso)
      ON DELETE cascade
      ON UPDATE cascade
);

