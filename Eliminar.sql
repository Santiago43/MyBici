/*Borrar persona - empleado - cliente*/
delete from persona where cedula =1;

/*Borrar usuario*/
delete from usuario where usuario="";

/*Borrar dirección*/
delete from direccion where idDireccion="";

/*Borrar permiso*/
delete from permiso where idPermiso="";

delete from rol_has_permiso where Rol_idRol=1 and Permiso_idPermiso=2;

delete from usuario_has_permiso where Usuario_usuario= "admin" and Permiso_idPermiso=2;

/*Borrar proveedor*/
delete from proveedor where idProveedor =1;

/*Borrar una sede*/
delete from sede where idSede=1;
